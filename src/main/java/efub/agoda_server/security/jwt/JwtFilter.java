package efub.agoda_server.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import efub.agoda_server.user.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserRepository repo;

    public JwtFilter(JwtUtil jwtUtil, UserRepository repo) {
        this.jwtUtil = jwtUtil;
        this.repo = repo;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            try {
                Jws<Claims> jws = jwtUtil.parseToken(token);
                Long userId = jws.getBody().get("userId", Long.class);
                repo.findById(userId).ifPresent(user -> {
                    var auth = new UsernamePasswordAuthenticationToken(
                            user, null, Collections.emptyList()
                    );
                    auth.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );
                    SecurityContextHolder.getContext().setAuthentication(auth);
                });
            } catch (Exception ignored) {
            }
        }
        filterChain.doFilter(request, response);
    }
}
