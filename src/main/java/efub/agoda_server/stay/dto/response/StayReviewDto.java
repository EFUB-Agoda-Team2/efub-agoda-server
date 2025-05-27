package efub.agoda_server.stay.dto.response;

import efub.agoda_server.stay.domain.Stay;
import efub.agoda_server.stay.dto.summary.StayReviewSummary;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class StayReviewDto {
    private int reviewCnt;
    private double stayRating;
    private List<StayReviewSummary> reviews;

    public static StayReviewDto from(Stay stay, List<StayReviewSummary> reviewSummaries) {
        return StayReviewDto.builder()
                .reviewCnt(stay.getReviewCnt())
                .stayRating(stay.getRating())
                .reviews(reviewSummaries)
                .build();
    }
}
