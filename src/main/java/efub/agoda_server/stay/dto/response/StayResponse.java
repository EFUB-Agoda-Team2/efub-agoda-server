package efub.agoda_server.stay.dto.response;

import efub.agoda_server.stay.domain.Room;
import efub.agoda_server.stay.domain.Stay;
import efub.agoda_server.stay.domain.StayImage;
import efub.agoda_server.stay.dto.summary.RoomSummary;
import efub.agoda_server.stay.dto.summary.StayReviewRatingSummary;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class StayResponse {
    private Long id;
    private String name;
    private String detail;
    private String address;
    private int salePrice;
    private List<String> tags;
    private List<String> stayImgUrls;
    private List<RoomSummary> rooms;
    private StayReviewRatingSummary review;

    public static StayResponse from(Stay stay, List<StayImage> stayImages, List<Room> rooms) {
        List<RoomSummary> roomSummaries = rooms.stream()
                .map(RoomSummary::of)
                .collect(Collectors.toList());

        List<String> stayImgList = stayImages.stream()
                .map(stayImage -> stayImage.getStImage())
                .collect(Collectors.toList());

        StayReviewRatingSummary stayReviewSummary = StayReviewRatingSummary.from(stay);

        return StayResponse.builder()
                .id(stay.getStId())
                .name(stay.getName())
                .detail(stay.getDetail())
                .address(stay.getAddress())
                .salePrice(stay.getSalePrice())
                .tags(stay.getTags())
                .stayImgUrls(stayImgList)
                .rooms(roomSummaries)
                .review(stayReviewSummary)
                .build();
    }
}
