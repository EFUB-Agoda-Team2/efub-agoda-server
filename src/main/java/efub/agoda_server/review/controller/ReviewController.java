package efub.agoda_server.review.controller;

import efub.agoda_server.review.dto.request.ReviewCreateRequest;
import efub.agoda_server.review.dto.request.ReviewUpdateRequest;
import efub.agoda_server.review.dto.response.ReviewResponse;
import efub.agoda_server.review.service.ReviewService;
import efub.agoda_server.user.domain.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/rev")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    //리뷰 생성
    @PostMapping
    public ResponseEntity<Void> createReview(@AuthenticationPrincipal User user,
                                             @RequestBody @Valid ReviewCreateRequest request){
        System.out.println(request.getResId());
        Long revId = reviewService.createReview(user,request);
        return ResponseEntity.created(URI.create("/rev/" + revId)).build();
    }
    //리뷰 조회
    @GetMapping("/{revId}")
    public ResponseEntity<ReviewResponse> getReview(@AuthenticationPrincipal User user,
                                                    @PathVariable Long revId){
        ReviewResponse response = reviewService.getReview(user,revId);
        return ResponseEntity.ok(response);
    }
    //리뷰 수정
    @PatchMapping("/{revId}")
    public ResponseEntity<ReviewResponse> updateReview(@AuthenticationPrincipal User user,
                                                      @PathVariable Long revId,
                                                      @RequestBody ReviewUpdateRequest request){
        ReviewResponse response = reviewService.updateReview(user,revId,request);
        return ResponseEntity.ok(response);
    }

}
