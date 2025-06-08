package efub.agoda_server.stay.controller;

import efub.agoda_server.stay.dto.request.StaySearchRequest;
import efub.agoda_server.stay.dto.response.StayListResponse;
import efub.agoda_server.stay.dto.response.StayResponse;
import efub.agoda_server.stay.service.StayService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stays")
@RequiredArgsConstructor
public class StayController {
    private final StayService stayService;

    @GetMapping
    public ResponseEntity<StayListResponse> getAllStays(@Valid @ModelAttribute StaySearchRequest request){
        return ResponseEntity.ok(stayService.getAllStays(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StayResponse> getStay(@PathVariable("id") Long id){
        return ResponseEntity.ok(stayService.getStay(id));
    }
}
