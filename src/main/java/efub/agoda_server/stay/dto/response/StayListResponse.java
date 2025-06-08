package efub.agoda_server.stay.dto.response;

import efub.agoda_server.stay.domain.Stay;
import efub.agoda_server.stay.dto.request.StaySearchRequest;
import efub.agoda_server.stay.dto.summary.StaySummary;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class StayListResponse {
    private String search;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private List<StaySummary> stays;

    public static StayListResponse from(StaySearchRequest request, Page<Stay> stays, int totalDays) {
        List<StaySummary> staySummaries = stays.getContent().stream()
                .map(stay -> StaySummary.from(stay, totalDays))
                .collect(Collectors.toList());

        return StayListResponse.builder()
                .search(request.getCity())
                .checkIn(request.getCheckIn())
                .checkOut(request.getCheckOut())
                .stays(staySummaries)
                .build();
    }
}
