package ALCOHOLIC_BOOT.server.service;

import ALCOHOLIC_BOOT.server.dto.MemberRequestDTO;
import ALCOHOLIC_BOOT.server.dto.TokenDTO;
import ALCOHOLIC_BOOT.server.entity.User;
import ALCOHOLIC_BOOT.server.repository.UserRepositoryInterface;
import ALCOHOLIC_BOOT.server.token.TokenGenerator;
import ALCOHOLIC_BOOT.server.token.TokenService;
import ALCOHOLIC_BOOT.server.user.AbstractAuthnProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
@Slf4j
@RequiredArgsConstructor
@Service
public class SignService {
    private final AbstractAuthnProvider abstractAuthnProvider;
    private final TokenGenerator generator;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepositoryInterface userRepository;
    public TokenDTO login(MemberRequestDTO requestBody, HttpServletRequest request) throws Exception {
        UsernamePasswordAuthenticationToken primaryAuthentication = requestBody.toAuthentication();
        Authentication auth = abstractAuthnProvider.authenticate(primaryAuthentication);

        long refreshTokenExpiresIn = Long.parseLong(request.getHeader("refreshTokenExpiresIn"));
        String refreshToken = request.getHeader("RefreshToken");

        //refreshToken이 없을 경우, access token과 refresh token을 모두 생성한다.
        if(refreshToken == null)
            return generator.generateTokens(auth);
        if(refreshToken != null && tokenService.validateRefreshToken(refreshToken) && !tokenService.checkExpireTime(refreshTokenExpiresIn))
            return generator.generateTokens(auth);
        if(refreshToken != null && tokenService.validateRefreshToken(refreshToken) && tokenService.checkExpireTime(refreshTokenExpiresIn))
            return generator.generateAccessToken(auth);

        throw new RuntimeException("로그인 실패! location = SignService.login");
    }

    public void signup(MemberRequestDTO requestBody) {
        User user = requestBody.signup(passwordEncoder);
        userRepository.save(user);
    }
}
