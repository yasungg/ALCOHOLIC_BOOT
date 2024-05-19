package startline.server.controller;

import org.springframework.http.HttpStatus;
import startline.server.dto.MemberRequestDTO;
import startline.server.dto.TokenDTO;
import startline.server.exceptions.UserExistException;
import startline.server.repository.UserRepositoryInterface;
import startline.server.service.SignService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Slf4j
@Tag(name = "Sign Controller", description = "로그인 / 회원가입을 위한 컨트롤러입니다.")
@RequiredArgsConstructor
@RestController
@RequestMapping("/sign")
public class SignController {
    private final UserRepositoryInterface userRepository;
    private final SignService signService;

    @Operation(summary = "login", description = "로그인 엔드포인트입니다.")
    @PostMapping("/in")
    public ResponseEntity<TokenDTO> login(@Parameter(description = "사용자 ID = username, Password = password / body타입")
                                              @Valid @RequestBody MemberRequestDTO requestBody, HttpServletRequest request) throws Exception {
        log.info("signin postmapping 진입");
        TokenDTO result = signService.login(requestBody, request);
        return ResponseEntity.ok(result);
    }
    @Operation(summary = "signup", description = "회원가입 엔드포인트입니다.")
    @PostMapping("/up")
    public ResponseEntity<String> signup(@Parameter(description = "사용자 ID = username, Password = password, 이름 = name, 닉네임 = nickname, 휴대폰번호 = phonne / body")
                                              @Valid @RequestBody MemberRequestDTO requestBody, HttpServletResponse response) throws UserExistException, IOException {
        if(userRepository.findByUsername(requestBody.getUsername()) != null) throw new UserExistException();

        signService.signup(requestBody);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
