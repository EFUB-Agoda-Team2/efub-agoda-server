package efub.agoda_server.stay.service;

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
        Pageable pageable = PageRequest.of(page, 8);    //페이지당 데이터 수 8개 고정
        Page<Stay> stays = stayRepository.findBySalePriceBetweenAndCity(minPrice, maxPrice, city, pageable);
        return StayListResponse.from(city, checkIn, checkOut, stays);
    }
}
