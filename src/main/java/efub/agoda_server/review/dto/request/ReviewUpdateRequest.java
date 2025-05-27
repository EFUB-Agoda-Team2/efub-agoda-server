package efub.agoda_server.review.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ReviewUpdateRequest {
    @JsonProperty("addr_rating")
    private Integer addrRating;
    @JsonProperty("sani_rating")
    private Integer saniRating;
    @JsonProperty("serv_rating")
    private Integer servRating;
    @JsonProperty("review_txt")
    private String reviewText;
}