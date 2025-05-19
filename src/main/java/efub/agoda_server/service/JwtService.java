package efub.agoda_server.service;

import efub.agoda_server.domain.entity.User;
import efub.agoda_server.security.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class JwtService {
    private final JwtUtil jwtUtil;

    public Map<String, String> generateTokens(User user) {
        String access = jwtUtil.generateToken(user);
        String refresh = jwtUtil.generateRefreshToken(user);
        return Map.of(
                "access_token", access,
                "refresh_token", refresh
        );
    }
}