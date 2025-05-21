package efub.agoda_server.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // 타입(상태 코드, "메시지");
    // Default
    ERROR(400, "요청 처리에 실패했습니다."),

    //stay 관련 에러
    PAST_CHECKIN_DATE(400, "체크인 날짜는 오늘 이후여야 합니다."),
    INVALID_CHECKOUT_DATE(400, "체크아웃 날짜는 체크인 날짜보다 이후여야 합니다.");

    private final int status;
    private final String message;
}