package efub.agoda_server.stay.dto.response;

import efub.agoda_server.stay.domain.Room;
import efub.agoda_server.stay.domain.Stay;
import efub.agoda_server.stay.dto.summary.RoomSummary;
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
public class StayResponse {
    private Long id;
    private String name;
    private String detail;
    private String address;
    private int salePrice;
    private List<String> tags;
    private List<String> stayImgUrls;
    private String roomImgUrls;
    private List<RoomSummary> rooms;
    private StayReviewSummary review;

    //TODO: 이미지 경로 설정
    public static StayResponse from(Stay stay, List<Room> rooms) {
        List<RoomSummary> roomSummaries = rooms.stream()
                .map(RoomSummary::of)
                .collect(Collectors.toList());

        StayReviewSummary stayReviewSummary = StayReviewSummary.from(stay);

        return StayResponse.builder()
                .id(stay.getStId())
                .name(stay.getName())
                .detail(stay.getDetail())
                .address(stay.getAddress())
                .salePrice(stay.getSalePrice())
                .tags(stay.getTags())
                .stayImgUrls(List.of())
                .roomImgUrls("")
                .rooms(roomSummaries)
                .review(stayReviewSummary)
                .build();
    }
}
