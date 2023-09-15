package ALCOHOLIC_BOOT.server.controller;

import ALCOHOLIC_BOOT.server.dto.MemberRequestDTO;
import ALCOHOLIC_BOOT.server.dto.TokenDTO;
import ALCOHOLIC_BOOT.server.entity.User;
import ALCOHOLIC_BOOT.server.exceptions.UserExistException;
import ALCOHOLIC_BOOT.server.repository.UserRepositoryInterface;
import ALCOHOLIC_BOOT.server.service.SignService;
import ALCOHOLIC_BOOT.server.user.MashilangUserDetails;
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
    public ResponseEntity<TokenDTO> login(@Parameter(description = "사용자 ID = username, Password = password / body타입") @Valid @RequestBody MemberRequestDTO requestBody, HttpServletRequest request) {
        return null;
    }
    @Operation(summary = "signup", description = "회원가입 엔드포인트입니다.")
    @PostMapping("/up")
    public ResponseEntity<String> signup(@Parameter(description = "사용자 ID = username, Password = password, 이름 = name, 닉네임 = nickname, 휴대폰번호 = phonne / body")
                                              @Valid @RequestBody MemberRequestDTO requestBody, HttpServletResponse response) throws UserExistException {
        if(userRepository.findUsernameByUsername(requestBody.getUsername()).isPresent()) throw new UserExistException("이미 가입된 유저입니다.");

        signService.signup(requestBody);

        return ResponseEntity.ok("signup completed");
    }

}
