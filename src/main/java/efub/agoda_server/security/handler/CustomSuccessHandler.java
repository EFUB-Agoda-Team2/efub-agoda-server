package efub.agoda_server.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import efub.agoda_server.domain.entity.User;
import efub.agoda_server.security.oauth2.CustomOAuth2User;
import efub.agoda_server.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtService jwtService;
    private final ObjectMapper mapper = new ObjectMapper();

    public CustomSuccessHandler(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest req,
                                        HttpServletResponse res,
                                        Authentication auth) throws IOException {

        CustomOAuth2User oAuthUser = (CustomOAuth2User) auth.getPrincipal();
        User user = oAuthUser.getUser();
        Map<String, String> tokens = jwtService.generateTokens(user);

        Map<String, Object> userJson = new HashMap<>();
        userJson.put("id",          user.getUserId());
        userJson.put("email",       user.getEmail());
        userJson.put("username",    user.getUsername());
        userJson.put("profile_img", user.getProfileImg());

        Map<String, Object> body = new HashMap<>();
        body.put("token_type",     "Bearer");
        body.put("access_token",   tokens.get("access_token"));
        body.put("refresh_token",  tokens.get("refresh_token"));
        body.put("expires_in",     3600);
        body.put("issued_at",      OffsetDateTime.now().toString());
        body.put("user",           userJson);

        res.setContentType("application/json;charset=UTF-8");
        res.setStatus(HttpServletResponse.SC_OK);
        res.getWriter().write(mapper.writeValueAsString(body));
    }
}
