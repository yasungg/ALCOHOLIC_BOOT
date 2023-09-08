package ALCOHOLIC_BOOT.server.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionController {
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String, String>> badCredentialExceptionHandler(BadCredentialsException bad) {
        Map<String, String> err = new HashMap<>();
        err.put("message", bad.getMessage());
        return ResponseEntity.badRequest().body(err);
    }

}
