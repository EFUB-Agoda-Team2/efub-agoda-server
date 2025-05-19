package efub.agoda_server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/login")
public class AuthController {
    @GetMapping
    public void kakaoLogin(HttpServletResponse res) throws IOException {
        res.sendRedirect("/oauth2/authorization/kakao");
    }
}