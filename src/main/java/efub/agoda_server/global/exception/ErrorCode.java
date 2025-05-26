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

    //image 처리 에러
    FILE_UPLOAD_FAILED(500, "파일 업로드에 실패했습니다."),
    INVALID_IMAGE_EXTENSION(400, "지원하지 않는 이미지 형식입니다."),
    IMAGE_COUNT_EXCEEDED(400, "이미지는 최대 5장까지 업로드 가능합니다.");

    private final int status;
    private final String message;
}
