package ALCOHOLIC_BOOT.server.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public class ApiError {
    private final HttpStatus httpStatus;
    private final String message;
    private final ObjectMapper objectMapper;

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public String convertToJSON() {
        try {
            return objectMapper.writeValueAsString(new ApiError(httpStatus, message, objectMapper));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("ApiError RuntimeException!!");
    }
}
