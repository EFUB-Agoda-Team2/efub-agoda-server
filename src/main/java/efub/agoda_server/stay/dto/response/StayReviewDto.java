package efub.agoda_server.stay.dto.response;

import efub.agoda_server.review.domain.Review;
import efub.agoda_server.stay.domain.Stay;
import efub.agoda_server.stay.dto.summary.StayReviewSummary;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class StayReviewDto {
    private int reviewCnt;
    private double stayRating;
    private List<StayReviewSummary> reviews;

    public static StayReviewDto from(Stay stay, List<Review> reviews) {
        List<StayReviewSummary> reviewSummaries = reviews.stream()
                .map(review -> StayReviewSummary.from(review))
                .collect(Collectors.toList());

        return StayReviewDto.builder()
                .reviewCnt(stay.getReviewCnt())
                .stayRating(stay.getRating())
                .reviews(reviewSummaries)
                .build();
    }
}
