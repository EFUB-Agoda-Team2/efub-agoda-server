package efub.agoda_server.stay.dto.summary;

import efub.agoda_server.stay.domain.Stay;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class StayReviewSummary {
    private double rating;
    private int reviewCnt;
    private double addrRating;
    private double saniRating;
    private double servRating;

    public static StayReviewSummary from(Stay stay) {
        return StayReviewSummary.builder()
                .rating(stay.getRating())
                .reviewCnt(stay.getReviewCnt())
                .addrRating(stay.getAddrRating())
                .saniRating(stay.getSaniRating())
                .servRating(stay.getServRating())
                .build();
    }
}
