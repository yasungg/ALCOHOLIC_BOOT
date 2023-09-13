package ALCOHOLIC_BOOT.server.controller;

import ALCOHOLIC_BOOT.server.dto.MemberRequestDTO;
import ALCOHOLIC_BOOT.server.dto.TokenDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Authentication Controller", description = "인증을 위한 컨트롤러입니다.")
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/authn")
public class AuthnController {
    @Operation(summary = "login", description = "로그인 엔드포인트입니다.")
    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@Parameter(description = "사용자 ID = username, Password = password / body타입") @Valid @RequestBody MemberRequestDTO requestBody, HttpServletRequest request) {
        return null;
    }
}
