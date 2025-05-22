package efub.agoda_server.stay.dto.response;

import efub.agoda_server.stay.domain.Stay;
import efub.agoda_server.stay.dto.summary.StaySummary;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Page;

import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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

    public static StayListResponse from(String city, LocalDate checkIn, LocalDate checkOut, Page<Stay> stays) {
        //총 숙박일
        int totalDays = (int) ChronoUnit.DAYS.between(checkIn, checkOut);

        List<StaySummary> staySummarys = stays.getContent().stream()
                .map(stay -> StaySummary.from(stay, totalDays))
                .collect(Collectors.toList());

        return StayListResponse.builder()
                .search(city)
                .checkIn(checkIn)
                .checkOut(checkOut)
                .stays(staySummarys)
                .build();
    }
}
