package efub.agoda_server.review.controller;

import efub.agoda_server.review.dto.request.ReviewCreateRequest;
import efub.agoda_server.review.dto.request.ReviewUpdateRequest;
import efub.agoda_server.review.dto.response.ReviewResponse;
import efub.agoda_server.review.service.ReviewService;
import efub.agoda_server.user.domain.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/rev")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    //리뷰 생성
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> createReview(@AuthenticationPrincipal User user,
                                             @RequestPart("request") @Valid ReviewCreateRequest request,
                                             @RequestPart(value = "images", required = false) List<MultipartFile> images){
        System.out.println(request.getResId());
        Long revId = reviewService.createReview(user, request, images == null || images.isEmpty() ? Collections.emptyList() : images);
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
    @PatchMapping(value = "/{revId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ReviewResponse> updateReview(@AuthenticationPrincipal User user,
                                                       @PathVariable Long revId,
                                                       @RequestPart("request") @Valid ReviewUpdateRequest request,
                                                       @RequestPart(value = "images", required = false) List<MultipartFile> images){
        ReviewResponse response = reviewService.updateReview(user, revId, request, images == null || images.isEmpty() ? Collections.emptyList() : images);
        return ResponseEntity.ok(response);
    }
}