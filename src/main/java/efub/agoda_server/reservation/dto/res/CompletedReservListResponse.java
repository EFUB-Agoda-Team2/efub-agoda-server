package efub.agoda_server.reservation.dto.res;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
public class CompletedReservListResponse {
    private Long res_id;
    private Long st_id;
    private String st_img;
    private String st_name;
    private String st_city;
    private String check_in;
    private String check_out;
    private Boolean rev;
    private Long rev_id;
}
