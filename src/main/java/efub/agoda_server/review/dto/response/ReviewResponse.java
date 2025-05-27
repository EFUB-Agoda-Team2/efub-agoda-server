package efub.agoda_server.review.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ReviewResponse {
    private ReviewDto review;
    private ReservationDto reservation;

    @Getter
    @Builder
    public static class ReviewDto {
        private Long revId;
        private Long resId;
        private Double avgRating;
        private String createdAt;
        private String updatedAt;
        private String reviewTxt;
        private List<String> revImgUrls;
    }

    @Getter
    @Builder
    public static class ReservationDto {
        private Long resId;
        private Long stId;
        private String stName;
        private String stCity;
        private String checkIn;
        private String checkOut;
    }
}