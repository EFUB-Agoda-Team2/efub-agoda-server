package efub.agoda_server.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // Default
    ERROR(400, "요청 처리에 실패했습니다."),
    UNAUTHORIZED_ACCESS(401, "인증되지 않은 사용자입니다."),

    // stay 관련 에러
    PAST_CHECKIN_DATE(400, "체크인 날짜는 오늘 이후여야 합니다."),
    INVALID_CHECKOUT_DATE(400, "체크아웃 날짜는 체크인 날짜보다 이후여야 합니다."),
    STAY_NOT_FOUND(404, "숙박이 존재하지 않습니다."),
    ROOM_NOT_FOUND(404, "객실이 존재하지 않습니다."),

    // reservation 관련 에러
    RESERVATION_NOT_FOUND(404, "존재하지 않는 예약입니다."),

    // review 관련 에러
    REVIEW_ALREADY_EXISTS(400, "이미 해당 예약에 대한 리뷰가 존재합니다."),
    REVIEW_NOT_FOUND(404, "리뷰 정보를 찾을 수 없습니다."),
    REVIEW_ACCESS_DENIED(403, "리뷰 접근 권한이 없습니다."),
  
    //image 처리 에러
    FILE_UPLOAD_FAILED(500, "파일 업로드에 실패했습니다."),
    INVALID_IMAGE_EXTENSION(400, "지원하지 않는 이미지 형식입니다."),
    IMAGE_COUNT_EXCEEDED(400, "이미지는 최대 3장까지 업로드 가능합니다."),
    NO_FILE_PROVIDED(400, "파일이 없습니다.");

    private final int status;
    private final String message;
}
