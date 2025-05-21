package efub.agoda_server.stay.service;

import efub.agoda_server.global.exception.CustomException;
import efub.agoda_server.global.exception.ErrorCode;
import efub.agoda_server.stay.domain.Room;
import efub.agoda_server.stay.domain.Stay;
import efub.agoda_server.stay.dto.response.StayListResponse;
import efub.agoda_server.stay.dto.response.StayResponse;
import efub.agoda_server.stay.repository.RoomRepository;
import efub.agoda_server.stay.repository.StayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StayService {

    private final StayRepository stayRepository;
    private final RoomRepository roomRepository;

    public StayListResponse getAllStays(String city, int minPrice, int maxPrice, LocalDate checkIn, LocalDate checkOut, int page) {
        if(checkIn.isBefore(LocalDate.now()))
            throw new CustomException(ErrorCode.PAST_CHECKIN_DATE);
        if(checkIn.isAfter(checkOut) || checkIn.isEqual(checkOut))
            throw new CustomException(ErrorCode.INVALID_CHECKOUT_DATE);

        Pageable pageable = PageRequest.of(page, 8);    //페이지당 데이터 수 8개 고정
        Page<Stay> stays = stayRepository.findBySalePriceBetweenAndAddressContaining(minPrice, maxPrice, city, pageable);

        return StayListResponse.from(city, checkIn, checkOut, stays);
    }

    public StayResponse getStay(Long id) {
        Stay stay = findByStayId(id);
        List<Room> rooms = roomRepository.findByStay(stay);
        return StayResponse.from(stay, rooms);
    }

    public Stay findByStayId(Long id){
        return stayRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.STAY_NOT_FOUND));
    }
}
