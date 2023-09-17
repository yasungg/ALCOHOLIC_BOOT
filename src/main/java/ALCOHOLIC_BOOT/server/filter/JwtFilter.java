package ALCOHOLIC_BOOT.server.filter;

import ALCOHOLIC_BOOT.server.token.TokenService;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.naming.AuthenticationException;
import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestURI =request.getRequestURI();

        if(shouldBypassJwtFilter(requestURI)) {
            filterChain.doFilter(request, response);
        }

        String jwt = tokenService.resolveToken(request, response);
        try {
            if (StringUtils.hasText(jwt) && tokenService.validateToken(jwt)) {
                    Authentication authentication = tokenService.getAuthentication(jwt);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            throw new RuntimeException("인증 실패, location = jwt filter");
        }
    }
    private boolean shouldBypassJwtFilter(String requestURI) {
        List<String> permittedPatterns = List.of(
                "/sign/", "/login", "/forall/", "/v3/**", "/swagger-resources/",
                "/swagger-ui/", "/webjars/", "/swagger/", "/sign-api/exception"
        );
        return permittedPatterns.stream().anyMatch(requestURI::startsWith);
    }
}
