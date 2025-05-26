package efub.agoda_server.auth.controller;

import efub.agoda_server.user.domain.User;
import efub.agoda_server.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;

    @GetMapping("/login")
    public void kakaoLogin(HttpServletResponse res) throws IOException {
        res.sendRedirect("/oauth2/authorization/kakao");
    }

    // 🔸 로그아웃 (프론트에서 토큰 삭제 처리)
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@AuthenticationPrincipal User user) {
        if (user == null) {
            return ResponseEntity.status(401).build();  // 인증 실패
        }
        // 서버에 refresh token을 저장하고 있었다면 여기서 삭제 처리
        return ResponseEntity.noContent().build();  // 204
    }

    // 🔸 회원탈퇴 (DB에서 사용자 삭제)
    @DeleteMapping("/withdraw")
    public ResponseEntity<Void> withdraw(@AuthenticationPrincipal User user) {
        if (user == null) {
            return ResponseEntity.status(401).build();
        }
        userRepository.delete(user);
        return ResponseEntity.noContent().build();
    }
}
