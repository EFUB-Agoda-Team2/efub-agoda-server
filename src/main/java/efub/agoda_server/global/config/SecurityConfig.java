package efub.agoda_server.global.config;

import efub.agoda_server.global.exception.dto.ErrorDto;
import efub.agoda_server.security.handler.CustomFailHandler;
import efub.agoda_server.security.handler.CustomSuccessHandler;
import efub.agoda_server.security.jwt.JwtAuthenticationEntryPoint;
import efub.agoda_server.security.jwt.JwtFilter;
import efub.agoda_server.security.oauth2.CustomOAuth2UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;
    private final JwtAuthenticationEntryPoint jwtEntryPoint;
    private final CustomOAuth2UserService oauth2UserService;
    private final CustomSuccessHandler successHandler;
    private final CustomFailHandler failHandler;

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration c = new CorsConfiguration();
        c.setAllowCredentials(true);
        c.setAllowedOriginPatterns(List.of("http://localhost:5173", "https://efub-agoda.o-r.kr"));
        c.addAllowedHeader("*");
        c.addAllowedMethod("*");
        c.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", c);
        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sm -> sm.sessionCreationPolicy(
                        org.springframework.security.config.http.SessionCreationPolicy.STATELESS))
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers("/login/**", "/oauth2/**", "/stays/**").permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/login")
                        .authorizationEndpoint(end -> end.baseUri("/oauth2/authorization"))
                        .redirectionEndpoint(redir -> redir.baseUri("/login/oauth2/code/*"))
                        .userInfoEndpoint(ui -> ui.userService(oauth2UserService))
                        .successHandler(successHandler)
                        .failureHandler(failHandler)
                )
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(jwtEntryPoint)
                        .accessDeniedHandler(customAccessDeniedHandler())
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AccessDeniedHandler customAccessDeniedHandler() {
        return (HttpServletRequest request, HttpServletResponse response, AccessDeniedException ex) -> {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);

            ErrorDto errorDto = new ErrorDto(
                    LocalDateTime.now().toString(),
                    HttpStatus.FORBIDDEN.value(),
                    "ACCESS_DENIED",
                    "권한이 없습니다.",
                    request.getRequestURI()
            );
            new ObjectMapper().writeValue(response.getWriter(), errorDto);
        };
    }
}
