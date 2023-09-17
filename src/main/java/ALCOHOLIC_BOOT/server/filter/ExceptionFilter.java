package ALCOHOLIC_BOOT.server.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class ExceptionFilter extends OncePerRequestFilter {
    private final ObjectMapper objectMapper;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch(JwtException e) {
            setJwtErrorResponse(HttpStatus.UNAUTHORIZED , response, e);
        } catch(BadCredentialsException b) {
            setLoginErrorResponse(HttpStatus.UNAUTHORIZED , response, b);
        }
    }
    public void setJwtErrorResponse(HttpStatus status, HttpServletResponse response, Throwable e) throws ServletException, IOException{
        response.setStatus(status.value());
        response.setContentType("application/json; charset=UTF-8");

        ApiError jwtException = new ApiError(HttpStatus.UNAUTHORIZED, e.getMessage(), objectMapper);
        response.getWriter().write(jwtException.convertToJSON());
    }
    public void setLoginErrorResponse(HttpStatus status, HttpServletResponse response, Throwable b) throws ServletException, IOException{
        response.setStatus(status.value());
        response.setContentType("application/json; charset=UTF-8");

        ApiError NotFoundException = new ApiError(HttpStatus.UNAUTHORIZED, b.getMessage(), objectMapper);
        response.getWriter().write(NotFoundException.convertToJSON());
    }
}

