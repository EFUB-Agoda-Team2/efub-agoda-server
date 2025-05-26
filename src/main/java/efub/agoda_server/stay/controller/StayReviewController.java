package efub.agoda_server.stay.controller;

import efub.agoda_server.review.service.ReviewService;
import efub.agoda_server.stay.dto.response.StayReviewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/stays/{stayId}/reviews")
@RequiredArgsConstructor
public class StayReviewController {
    private final ReviewService reviewService;

    @GetMapping
    public ResponseEntity<StayReviewDto> getReviewsByStay(@PathVariable("stayId") Long stayId) {
        return ResponseEntity.ok(reviewService.getReviewsByStay(stayId));
    }
}
