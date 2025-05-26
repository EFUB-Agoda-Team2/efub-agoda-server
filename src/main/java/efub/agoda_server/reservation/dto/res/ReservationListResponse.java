package efub.agoda_server.reservation.dto.res;

import lombok.*;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class ReservationListResponse {
    private List<ReservationListItemResponse> upcoming_reservations;
    private List<ReservationListItemResponse> completed_reservations;
}
