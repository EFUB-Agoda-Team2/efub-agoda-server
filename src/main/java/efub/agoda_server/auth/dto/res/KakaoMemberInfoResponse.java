package efub.agoda_server.auth.dto.res;

import java.util.Map;

public class KakaoMemberInfoResponse {

    private final Map<String, Object> attrs;

    public KakaoMemberInfoResponse(Map<String, Object> attrs) {
        this.attrs = attrs;
    }

    @SuppressWarnings("unchecked")
    public String getEmail() {
        Map<String, Object> a = (Map<String, Object>) attrs.get("kakao_account");
        return a != null ? (String) a.get("email") : null;
    }
    @SuppressWarnings("unchecked")
    public String getNickname() {
        Map<String, Object> a = (Map<String, Object>) attrs.get("kakao_account");
        if (a != null) {
            Map<String, Object> p = (Map<String, Object>) a.get("profile");
            return p != null ? (String) p.get("nickname") : null;
        }
        return null;
    }
    @SuppressWarnings("unchecked")
    public String getProfileImageUrl() {
        Map<String, Object> a = (Map<String, Object>) attrs.get("kakao_account");
        if (a != null) {
            Map<String, Object> p = (Map<String, Object>) a.get("profile");
            return p != null ? (String) p.get("profile_image_url") : null;
        }
        return null;
    }
}