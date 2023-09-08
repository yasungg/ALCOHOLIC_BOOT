package ALCOHOLIC_BOOT.server.controller;

import ALCOHOLIC_BOOT.server.dto.MemberRequestDTO;
import ALCOHOLIC_BOOT.server.dto.TokenDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/authn")
public class AuthnController {
    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@Valid @RequestBody MemberRequestDTO request) {
        return null;
    }
}
