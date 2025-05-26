package efub.agoda_server.reservation.dto.req;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationCreateRequest {
    @NotNull
    private Long st_id;

    @NotNull
    private Long room_id;

    @NotBlank
    private String guest_first;

    @NotBlank
    private String guest_last;

    @Email
    @NotBlank
    private String guest_email;

    @NotBlank
    private String guest_phone;

    private String guest_request;

    @Min(1)
    private int install_month;

    @NotNull
    private String checkin_at;

    @NotNull
    private String checkout_at;
}
