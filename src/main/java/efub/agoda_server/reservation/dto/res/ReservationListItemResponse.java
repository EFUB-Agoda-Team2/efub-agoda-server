package efub.agoda_server.reservation.dto.res;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
public class ReservationListItemResponse {
    private Long res_id;
    private Long st_id;
    private String st_name;
    private String st_city;
    private String check_in;
    private String check_out;
}
