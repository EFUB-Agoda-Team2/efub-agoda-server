package efub.agoda_server.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import efub.agoda_server.user.domain.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    private final SecretKey secretKey;

    public JwtUtil(@Value("${SECRET_KEY}") String b64) {
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(b64));
    }

    public String generateToken(User u) {
        Date now = new Date();
        // 1h
        long ACCESS_EXP_MS = 3600_000;
        return Jwts.builder()
                .claim("userId", u.getUserId())
                .claim("email", u.getEmail())
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + ACCESS_EXP_MS))
                .signWith(secretKey)
                .compact();
    }

    public String generateRefreshToken(User u) {
        Date now = new Date();
        // 7d
        long REFRESH_EXP_MS = 604_800_000;
        return Jwts.builder()
                .claim("userId", u.getUserId())
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + REFRESH_EXP_MS))
                .signWith(secretKey)
                .compact();
    }

    public Jws<Claims> parseToken(String tok) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(tok);
    }
}