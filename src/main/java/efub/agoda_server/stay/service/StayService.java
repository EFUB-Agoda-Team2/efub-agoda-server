package efub.agoda_server.stay.service;

import  efub.agoda_server.global.exception.CustomException;
import efub.agoda_server.global.exception.ErrorCode;
import efub.agoda_server.review.domain.Review;
import efub.agoda_server.stay.domain.Room;
import efub.agoda_server.stay.domain.Stay;
import efub.agoda_server.stay.domain.StayImage;
import efub.agoda_server.stay.dto.response.StayListResponse;
import efub.agoda_server.stay.dto.response.StayResponse;
import efub.agoda_server.stay.dto.summary.StaySummary;
import efub.agoda_server.stay.repository.RoomRepository;
import efub.agoda_server.stay.repository.StayImageRepository;
import efub.agoda_server.stay.repository.StayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StayService {

    private final StayRepository stayRepository;
    private final RoomRepository roomRepository;
    private final StayImageRepository stayImageRepository;

    @Transactional(readOnly = true)
    public StayListResponse getAllStays(String city, int minPrice, int maxPrice, LocalDate checkIn, LocalDate checkOut, int page) {
        validateCheckInOutDate(checkIn, checkOut);

        Pageable pageable = PageRequest.of(page, 8);    //페이지당 데이터 수 8개 고정
        Page<Stay> stays = stayRepository.findBySalePriceBetweenAndAddressContaining(minPrice, maxPrice, city, pageable);

        int totalDays = (int) ChronoUnit.DAYS.between(checkIn, checkOut);   //총 숙박일
        List<StaySummary> staySummaries = stays.getContent().stream()
                .map(stay -> {
                    return StaySummary.from(stay, totalDays);
                })
                .collect(Collectors.toList());

        return StayListResponse.from(city, checkIn, checkOut, staySummaries);
    }

    @Transactional(readOnly = true)
    public StayResponse getStay(Long id) {
        Stay stay = findByStayId(id);
        List<StayImage> stayImages = stayImageRepository.findAllByStay(stay);
        List<Room> rooms = roomRepository.findAllByStay(stay);
        return StayResponse.from(stay, stayImages, rooms);
    }

    @Transactional
    public void updateRatingForNewReview(Review review){
        Stay searchStay = findByStayId(review.getReservation().getStay().getStId());
        int prevReviewCnt = searchStay.getReviewCnt();

        double newAddrRating = calculateReviewAvg(searchStay.getAddrRating(), review.getAddrRating(), prevReviewCnt);
        double newSaniRating = calculateReviewAvg(searchStay.getSaniRating(), review.getSaniRating(), prevReviewCnt);
        double newServRating = calculateReviewAvg(searchStay.getServRating(), review.getServRating(), prevReviewCnt);
        double newRating = (newAddrRating + newSaniRating + newServRating) / 3;
        searchStay.updateReview(newAddrRating, newSaniRating, newServRating, newRating);
    }

    @Transactional
    public void updateRatingForEditReview(Review oldReview, Review updatedReview){
        Stay searchStay = findByStayId(updatedReview.getReservation().getStay().getStId());
        int reviewCnt = searchStay.getReviewCnt();

        double newAddrRating = recalculateAvgAfterEdit(searchStay.getAddrRating(), oldReview.getAddrRating(), updatedReview.getAddrRating(), reviewCnt);
        double newSaniRating = recalculateAvgAfterEdit(searchStay.getSaniRating(), oldReview.getSaniRating(), updatedReview.getSaniRating(), reviewCnt);
        double newServRating = recalculateAvgAfterEdit(searchStay.getServRating(), oldReview.getServRating(), updatedReview.getServRating(), reviewCnt);
        double newRating = (newAddrRating + newSaniRating + newServRating) / 3;

        searchStay.updateReviewAfterEdit(newAddrRating, newSaniRating, newServRating, newRating);
    }

    public Stay findByStayId(Long id){
        return stayRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.STAY_NOT_FOUND));
    }

    public void validateCheckInOutDate(LocalDate checkIn, LocalDate checkOut){
        if(checkIn.isBefore(LocalDate.now()))
            throw new CustomException(ErrorCode.PAST_CHECKIN_DATE);
        if(checkIn.isAfter(checkOut) || checkIn.isEqual(checkOut))
            throw new CustomException(ErrorCode.INVALID_CHECKOUT_DATE);
    }

    public double calculateReviewAvg(double prevAvg, double newValue, int prevCnt){
        return (prevAvg * prevCnt + newValue) / (prevCnt + 1);
    }

    public double recalculateAvgAfterEdit(double oldAvg, double oldValue, double newValue, int count) {
        if (count == 0)
            return newValue; // 0으로 나누는 경우 방지
        return (oldAvg * count - oldValue + newValue) / count;
    }
}
