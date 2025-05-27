package efub.agoda_server.review.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import efub.agoda_server.reservation.domain.Reservation;
import efub.agoda_server.review.domain.Review;
import efub.agoda_server.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class ReviewCreateRequest {
    @JsonProperty("res_id")
    private Long resId;

    @JsonProperty("addr_rating")
    private int addrRating;

    @JsonProperty("sani_rating")
    private int saniRating;

    @JsonProperty("serv_rating")
    private int servRating;

    @JsonProperty("review_txt")
    private String reviewText;

    @JsonProperty("revImg_urls")
    private List<String> revImgUrls;

    public Review toEntity(Reservation reservation, User user) {
        return Review.builder()
                .addrRating(addrRating)
                .saniRating(saniRating)
                .servRating(servRating)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .text(reviewText)
                .reservation(reservation)
                .user(user)
                .build();
    }
}
