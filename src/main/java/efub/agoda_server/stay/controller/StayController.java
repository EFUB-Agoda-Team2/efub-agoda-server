package efub.agoda_server.stay.controller;

import efub.agoda_server.stay.dto.response.StayListResponse;
import efub.agoda_server.stay.dto.response.StayResponse;
import efub.agoda_server.stay.service.StayService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/stays")
@RequiredArgsConstructor
public class StayController {
    private final StayService stayService;

    @GetMapping
    public ResponseEntity<StayListResponse> getAllStays(@RequestParam("city") String city,
                                                     @RequestParam(value = "minPrice", defaultValue = "0") int minPrice,
                                                     @RequestParam(value = "maxPrice", defaultValue = "2147483647") int maxPrice,
                                                     @RequestParam("checkIn") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate checkIn,
                                                     @RequestParam("checkOut") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate checkOut,
                                                     @RequestParam(value = "page", defaultValue = "0") int page){
        return ResponseEntity.ok(stayService.getAllStays(city, minPrice, maxPrice, checkIn, checkOut, page));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StayResponse> getStay(@PathVariable("id") Long id){
        return ResponseEntity.ok(stayService.getStay(id));
    }
}
