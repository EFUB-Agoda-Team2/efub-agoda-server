package efub.agoda_server.review.service;

import efub.agoda_server.review.domain.Review;
import efub.agoda_server.review.repository.ReviewRepository;
import efub.agoda_server.stay.domain.Stay;
import efub.agoda_server.stay.dto.response.StayReviewDto;
import efub.agoda_server.stay.service.StayService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private static ReviewRepository reviewRepository;
    private static StayService stayService;

    @Transactional(readOnly = true)
    public StayReviewDto getReviewsByStay(Long stayId) {
        Stay searchStay = stayService.findByStayId(stayId);
        List<Review> reviews = reviewRepository.findByStay(searchStay);

        return StayReviewDto.from(searchStay, reviews);
    }
}
