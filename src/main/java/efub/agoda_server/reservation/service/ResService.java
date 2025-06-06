package efub.agoda_server.reservation.service;

import efub.agoda_server.global.exception.CustomException;
import efub.agoda_server.global.exception.ErrorCode;
import efub.agoda_server.reservation.domain.Reservation;
import efub.agoda_server.reservation.dto.req.ReservationCreateRequest;
import efub.agoda_server.reservation.dto.res.ReservationListItemResponse;
import efub.agoda_server.reservation.dto.res.ReservationListResponse;
import efub.agoda_server.reservation.dto.res.ReservationResponse;
import efub.agoda_server.reservation.repository.ResRepository;
import efub.agoda_server.stay.domain.Room;
import efub.agoda_server.stay.domain.Stay;
import efub.agoda_server.stay.repository.RoomRepository;
import efub.agoda_server.stay.repository.StayRepository;
import efub.agoda_server.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ResService {

    private final StayRepository stayRepo;
    private final RoomRepository roomRepo;
    private final ResRepository resRepo;

    @Transactional
    public ReservationResponse createReservation(User user, ReservationCreateRequest req) {
        Stay stay = stayRepo.findById(req.getSt_id())
                .orElseThrow(() -> new CustomException(ErrorCode.STAY_NOT_FOUND));

        Room room = roomRepo.findById(req.getRoom_id())
                .orElseThrow(() -> new CustomException(ErrorCode.STAY_NOT_FOUND));

        LocalDate checkin = LocalDate.parse(req.getCheckin_at());
        LocalDate checkout = LocalDate.parse(req.getCheckout_at());

//        if (checkin.isBefore(LocalDate.now())) {
//            throw new CustomException(ErrorCode.PAST_CHECKIN_DATE);
//        }

        if (!checkout.isAfter(checkin)) {
            throw new CustomException(ErrorCode.INVALID_CHECKOUT_DATE);
        }

        Reservation reservation = Reservation.builder()
                .user(user)
                .stay(stay)
                .room(room)
                .guestFirst(req.getGuest_first())
                .guestLast(req.getGuest_last())
                .guestEmail(req.getGuest_email())
                .guestPhone(req.getGuest_phone())
                .guestRequest(req.getGuest_request())
                .installMonth(req.getInstall_month())
                .createdAt(LocalDateTime.now())
                .checkinAt(checkin)
                .checkoutAt(checkout)
                .build();

        Reservation saved = resRepo.save(reservation);

        return ReservationResponse.builder()
                .res_id(saved.getResId())
                .st_id(stay.getStId())
                .room_id(room.getRoomId())
                .guest_first(saved.getGuestFirst())
                .guest_last(saved.getGuestLast())
                .guest_email(saved.getGuestEmail())
                .guest_phone(saved.getGuestPhone())
                .guest_request(saved.getGuestRequest())
                .install_month(saved.getInstallMonth())
                .created_at(saved.getCreatedAt().toString())
                .checkin_at(saved.getCheckinAt().toString())
                .checkout_at(saved.getCheckoutAt().toString())
                .build();
    }

    @Transactional(readOnly = true)
    public ReservationListResponse getUserReservations(User user) {
        LocalDate today = LocalDate.now();

        List<Reservation> all = resRepo.findAllByUser(user);

        List<ReservationListItemResponse> upcoming = all.stream()
                .filter(r -> !r.getCheckinAt().isBefore(today))  // today or future
                .sorted((r1, r2) -> r1.getCheckinAt().compareTo(r2.getCheckinAt()))  // 가까운 미래순
                .map(this::toDto)
                .toList();

        List<ReservationListItemResponse> completed = all.stream()
                .filter(r -> r.getCheckinAt().isBefore(today))  // past
                .sorted((r1, r2) -> r2.getCheckinAt().compareTo(r1.getCheckinAt()))  // 가까운 과거순
                .map(this::toDto)
                .toList();

        return ReservationListResponse.builder()
                .upcoming_reservations(upcoming)
                .completed_reservations(completed)
                .build();
    }

    @Transactional(readOnly = true)
    public List<ReservationListItemResponse> getCompletedUserReservations(User user) {
        LocalDate today = LocalDate.now();
        List<Reservation> all = resRepo.findAllByUser(user);
        List<ReservationListItemResponse> completed = all.stream()
                .filter(r -> r.getCheckinAt().isBefore(today))  // past
                .sorted((r1, r2) -> r2.getCheckinAt().compareTo(r1.getCheckinAt()))  // 가까운 과거순
                .map(this::toDto)
                .toList();
        return completed;
    }

    private ReservationListItemResponse toDto(Reservation r) {
        return ReservationListItemResponse.builder()
                .res_id(r.getResId())
                .st_id(r.getStay().getStId())
                .st_name(r.getStay().getName())
                .st_city(r.getStay().getCity())
                .check_in(r.getCheckinAt().toString())
                .check_out(r.getCheckoutAt().toString())
                .build();
    }
}
