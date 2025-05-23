package efub.agoda_server.stay.dto.response;

import efub.agoda_server.stay.dto.summary.StaySummary;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class StayListResponse {
    private String search;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private List<StaySummary> stays;

    public static StayListResponse from(String city, LocalDate checkIn, LocalDate checkOut, List<StaySummary> staySummaries) {
        return StayListResponse.builder()
                .search(city)
                .checkIn(checkIn)
                .checkOut(checkOut)
                .stays(staySummaries)
                .build();
    }
}
