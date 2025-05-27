package efub.agoda_server.review.service;

import efub.agoda_server.review.domain.Review;
import efub.agoda_server.review.repository.ReviewRepository;
import efub.agoda_server.stay.domain.Stay;
import efub.agoda_server.stay.dto.response.StayReviewDto;
import efub.agoda_server.stay.service.StayService;
import efub.agoda_server.global.exception.CustomException;
import efub.agoda_server.global.exception.ErrorCode;
import efub.agoda_server.reservation.domain.Reservation;
import efub.agoda_server.reservation.repository.ResRepository;
import efub.agoda_server.review.domain.ReviewImg;
import efub.agoda_server.review.dto.request.ReviewCreateRequest;
import efub.agoda_server.review.dto.request.ReviewUpdateRequest;
import efub.agoda_server.review.dto.response.ReviewResponse;
import efub.agoda_server.review.repository.ReviewImgRepository;
import efub.agoda_server.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewImgRepository reviewImgRepository;
    private final ResRepository resRepository;
    private final StayService stayService;

    @Transactional
    public Long createReview(User user, ReviewCreateRequest request){
        Reservation reservation = resRepository.findById(request.getResId())
                .orElseThrow(() ->  new CustomException(ErrorCode.RESERVATION_NOT_FOUND));
        Review review = request.toEntity(reservation, user);
        reviewRepository.save(review);

        List<ReviewImg> reviewImgs = request.getRevImgUrls().stream()
                .map(url -> ReviewImg.builder()
                        .revImage(url)
                        .review(review)
                        .build())
                .toList();
        reviewImgRepository.saveAll(reviewImgs);
        stayService.updateReviewRating(review);
        return review.getRevId();
    }

    @Transactional(readOnly = true)
    public ReviewResponse getReview(User user, Long revId){
        Review review = reviewRepository.findById(revId)
                .orElseThrow(() -> new CustomException(ErrorCode.REVIEW_NOT_FOUND));

        if (!review.getUser().getUserId().equals(user.getUserId())) {
            throw new CustomException(ErrorCode.REVIEW_ACCESS_DENIED);
        }
        return buildReviewResponse(review);
    }

    @Transactional
    public ReviewResponse updateReview(User user, Long revId, ReviewUpdateRequest request) {
        Review review = reviewRepository.findById(revId)
                .orElseThrow(() -> new CustomException(ErrorCode.REVIEW_NOT_FOUND));

        if (!review.getUser().getUserId().equals(user.getUserId())) {
            throw new CustomException(ErrorCode.REVIEW_ACCESS_DENIED);
        }
        review.updateAll(
                request.getAddrRating(),
                request.getSaniRating(),
                request.getServRating(),
                request.getReviewText(),
                LocalDateTime.now()
        );
        if (request.getRevImgUrls() != null) {
            reviewImgRepository.deleteAllByReview(review);
            List<ReviewImg> newImages = request.getRevImgUrls().stream()
                    .map(url -> ReviewImg.builder()
                            .revImage(url)
                            .review(review)
                            .build())
                    .toList();
            reviewImgRepository.saveAll(newImages);
        }
        return buildReviewResponse(review);
    }

    private ReviewResponse buildReviewResponse(Review review) {
        List<String> revImgUrls = reviewImgRepository.findAllByReview(review)
                .stream()
                .map(ReviewImg::getRevImage)
                .toList();

        double avgRating = (review.getAddrRating() +
                review.getSaniRating() +
                review.getServRating()) / 3.0;

        Reservation reservation = review.getReservation();
        Stay stay = reservation.getStay();

        return ReviewResponse.builder()
                .review(
                        ReviewResponse.ReviewDto.builder()
                                .revId(review.getRevId())
                                .resId(reservation.getResId())
                                .avgRating(avgRating)
                                .createdAt(review.getCreatedAt().toString())
                                .updatedAt(review.getUpdatedAt().toString())
                                .reviewTxt(review.getText())
                                .revImgUrls(revImgUrls)
                                .build()
                )
                .reservation(
                        ReviewResponse.ReservationDto.builder()
                                .resId(reservation.getResId())
                                .stId(stay.getStId())
                                .stName(stay.getName())
                                .stCity(stay.getCity())
                                .checkIn(reservation.getCheckinAt().toString())
                                .checkOut(reservation.getCheckoutAt().toString())
                                .build()
                )
                .build();
    }

    @Transactional(readOnly = true)
    public StayReviewDto getReviewsByStay(Long stayId) {
        Stay searchStay = stayService.findByStayId(stayId);
        List<Review> reviews = reviewRepository.findByStay(searchStay);

        return StayReviewDto.from(searchStay, reviews);
    }
}
