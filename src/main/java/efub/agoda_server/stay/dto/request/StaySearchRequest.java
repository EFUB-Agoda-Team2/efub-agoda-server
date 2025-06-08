package efub.agoda_server.stay.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
public class StaySearchRequest {

    @NotBlank(message = "도시명은 필수입니다.")
    private String city;

    @Min(value = 0, message = "최소 가격은 0 이상이어야 합니다.")
    private Integer minPrice;

    @Max(value = Integer.MAX_VALUE)
    private Integer maxPrice;

    @NotNull(message = "체크인 날짜는 필수입니다.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate checkIn;

    @NotNull(message = "체크아웃 날짜는 필수입니다.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate checkOut;

    @Min(value = 0, message = "페이지는 0 이상이어야 합니다.")
    private Integer page;

    public StaySearchRequest(String city, Integer minPrice, Integer maxPrice, LocalDate checkIn, LocalDate checkOut, Integer page) {
        this.city = city;
        this.minPrice = (minPrice == null) ? 0 : minPrice;
        this.maxPrice = (maxPrice == null) ? Integer.MAX_VALUE : maxPrice;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.page = (page == null) ? 0 : page;
    }
}
