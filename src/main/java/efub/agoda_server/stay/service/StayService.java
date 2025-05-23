package efub.agoda_server.stay.service;

import  efub.agoda_server.global.exception.CustomException;
import efub.agoda_server.global.exception.ErrorCode;
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
        //TODO: 이미지 불러올 때 n+1 방식 생길 것 같아 추후 리팩토링 필요
        List<StaySummary> staySummaries = stays.getContent().stream()
                .map(stay -> {
                    StayImage img = stayImageRepository.findFirstByStay(stay);  //대표 이미지 추가
                    return StaySummary.from(stay, totalDays, img != null ? img.getStImage() : null);
                })
                .collect(Collectors.toList());

        return StayListResponse.from(city, checkIn, checkOut, staySummaries);
    }

    @Transactional(readOnly = true)
    public StayResponse getStay(Long id) {
        Stay stay = findByStayId(id);
        List<StayImage> stayImages = stayImageRepository.findByStay(stay);
        List<Room> rooms = roomRepository.findByStay(stay);
        return StayResponse.from(stay, stayImages, rooms);
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
}
