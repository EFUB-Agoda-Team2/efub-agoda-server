package efub.agoda_server.stay.service;

import efub.agoda_server.global.exception.CustomException;
import efub.agoda_server.global.exception.ErrorCode;
import efub.agoda_server.stay.domain.Stay;
import efub.agoda_server.stay.dto.response.StayListResponse;
import efub.agoda_server.stay.repository.StayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StayService {

    private final StayRepository stayRepository;

    @Transactional(readOnly = true)
    public StayListResponse getStays(String city, int minPrice, int maxPrice, LocalDate checkIn, LocalDate checkOut, int page) {
        if(checkIn.isBefore(LocalDate.now()))
            throw new CustomException(ErrorCode.PAST_CHECKIN_DATE);
        if(checkIn.isAfter(checkOut) || checkIn.isEqual(checkOut))
            throw new CustomException(ErrorCode.INVALID_CHECKOUT_DATE);
        Pageable pageable = PageRequest.of(page, 2);    //페이지당 데이터 수 8개 고정
        Page<Stay> stays = stayRepository.findBySalePriceBetweenAndCity(minPrice, maxPrice, city, pageable);
        return StayListResponse.from(city, checkIn, checkOut, stays);
    }
}
