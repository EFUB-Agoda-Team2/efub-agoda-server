package efub.agoda_server.stay.dto.summary;

import efub.agoda_server.stay.domain.Stay;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class StayReviewRatingSummary {
    private double rating;
    private int reviewCnt;
    private double addrRating;
    private double saniRating;
    private double servRating;

    public static StayReviewRatingSummary from(Stay stay) {
        return StayReviewRatingSummary.builder()
                .rating(stay.getRating())
                .reviewCnt(stay.getReviewCnt())
                .addrRating(stay.getAddrRating())
                .saniRating(stay.getSaniRating())
                .servRating(stay.getServRating())
                .build();
    }
}
