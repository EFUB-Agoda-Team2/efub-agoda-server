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
    ROOM_NOT_FOUND(404, "객실이 존재하지 않습니다.");

    private final int status;
    private final String message;
}
