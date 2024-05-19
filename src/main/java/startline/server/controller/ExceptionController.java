package startline.server.controller;

import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import startline.server.exceptions.UserExistException;

import java.util.HashMap;
import java.util.Map;
@Slf4j
@RestControllerAdvice
public class ExceptionController {
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String, String>> badCredentialExceptionHandler(BadCredentialsException bad) {

        return ResponseEntity.badRequest().build();
    }
    @ExceptionHandler(JwtException.class)
    public ResponseEntity<Map<String, String>> JwtExceptionHandler(JwtException jwt) {
        log.error(jwt.getMessage());

        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Map<String, String>> UsernameNotFoundExceptionHandler(UsernameNotFoundException userNotFound) {
        log.error(userNotFound.getMessage());

        return ResponseEntity
                .badRequest()
                .header("X-Error-Code", "613")
                .build();
    }
    @ExceptionHandler(UserExistException.class)
    public ResponseEntity<Map<String, String>> UserExistExceptionHandler(UserExistException userExist) {
        log.error(userExist.getMessage());

        return ResponseEntity
                .badRequest()
                .header("X-Error-Code", userExist.getErrorCode())
                .build();
    }
}
