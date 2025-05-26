package efub.agoda_server.reservation.controller;

import efub.agoda_server.reservation.dto.req.ReservationCreateRequest;
import efub.agoda_server.reservation.dto.res.ReservationListResponse;
import efub.agoda_server.reservation.dto.res.ReservationResponse;
import efub.agoda_server.reservation.service.ResService;
import efub.agoda_server.user.domain.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reserv")
@RequiredArgsConstructor
public class ResController {

    private final ResService resService;

    @PostMapping
    public ResponseEntity<ReservationResponse> createReservation(
            @AuthenticationPrincipal User user,
            @RequestBody @Valid ReservationCreateRequest req) {
        ReservationResponse res = resService.createReservation(user, req);
        return ResponseEntity.status(201).body(res);
    }

    @GetMapping
    public ResponseEntity<ReservationListResponse> getMyReservations(
            @AuthenticationPrincipal User user) {
        ReservationListResponse res = resService.getUserReservations(user);
        return ResponseEntity.ok(res);
    }
}
