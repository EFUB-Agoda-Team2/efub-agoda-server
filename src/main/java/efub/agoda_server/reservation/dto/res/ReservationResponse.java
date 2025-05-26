package efub.agoda_server.reservation.dto.res;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
public class ReservationResponse {
    private Long res_id;
    private Long st_id;
    private Long room_id;
    private String guest_first;
    private String guest_last;
    private String guest_email;
    private String guest_phone;
    private String guest_request;
    private int install_month;
    private String created_at;
    private String checkin_at;
    private String checkout_at;
}
