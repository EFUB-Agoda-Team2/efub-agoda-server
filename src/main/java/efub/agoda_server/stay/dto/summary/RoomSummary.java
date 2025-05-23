package efub.agoda_server.stay.dto.summary;

import efub.agoda_server.stay.domain.Room;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RoomSummary {
    private String name;
    private String bed;
    private int roomPrice;
    private int roomSalePrice;
    private String roomImgUrl;

    public static RoomSummary of(Room room){
        return RoomSummary.builder()
                .name(room.getName())
                .bed(room.getBed())
                .roomPrice(room.getPrice())
                .roomSalePrice(room.getSalePrice())
                .roomImgUrl(room.getRoomImage())
                .build();
    }
}
