package efub.agoda_server.security.oauth2;

import efub.agoda_server.user.domain.User;
import efub.agoda_server.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final AuthService authService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest req) {
        OAuth2User o = super.loadUser(req);
        Map<String, Object> attrs = o.getAttributes();
        User user = authService.upsertKakaoUser(attrs);
        return new CustomOAuth2User(user, attrs, "id");
    }
}