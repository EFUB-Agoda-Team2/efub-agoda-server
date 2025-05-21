package efub.agoda_server.stay.dto.summary;

import efub.agoda_server.stay.domain.Stay;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class StaySummary {
    private Long id;
    private String name;
    private String address;
    private Double rating;
    private int reviewCnt;
    private int price;
    private int salePrice;
    private int totalPrice;
    private List<String> tags;
    private List<String> stayImgUrls;

    //TODO: 이미지 경로 변경
    public static StaySummary from(Stay stay, int totalDays){
        return StaySummary.builder()
                .id(stay.getStId())
                .name(stay.getName())
                .address(stay.getAddress())
                .rating(stay.getRating())
                .reviewCnt(stay.getReviewCnt())
                .price(stay.getPrice())
                .salePrice(stay.getSalePrice())
                .totalPrice(stay.getSalePrice() * totalDays)
                .tags(stay.getTags())
                .stayImgUrls(List.of())
                .build();
    }
}
