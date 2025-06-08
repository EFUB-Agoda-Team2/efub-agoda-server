package efub.agoda_server.stay.dto.summary;

import efub.agoda_server.review.domain.Review;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class StayReviewSummary {
    private String reviewer;
    private String reviewerImg;
    private double score;
    private String reviewText;
    private LocalDate createdAt;
    private List<String> reviewImgUrls;

    //TODO: reviewImg repository 생성 후 경로 설정 필요
    public static StayReviewSummary from(Review review, List<String> reviewImgUrls) {
        return StayReviewSummary.builder()
                .reviewer(review.getUser().getUsername())
                .reviewerImg(review.getUser().getProfileImg())
                .score(calculateTotalRating(review))
                .reviewText(review.getText())
                .createdAt(review.getCreatedAt().toLocalDate())
                .reviewImgUrls(reviewImgUrls)
                .build();
    }

    private static double calculateTotalRating(Review review){
        return (review.getAddrRating() + review.getServRating() + review.getSaniRating()) / 3;
    }
}
