package ALCOHOLIC_BOOT.server.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class TokenDTO {
    private String grantType;
    private String accessToken;
    private LocalDateTime tokenExpiresIn;
}
