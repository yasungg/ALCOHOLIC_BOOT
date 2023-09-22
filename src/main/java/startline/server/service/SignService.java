package startline.server.service;

import startline.server.constant.AuthorityName;
import startline.server.dto.MemberRequestDTO;
import startline.server.dto.TokenDTO;
import startline.server.entity.User;
import startline.server.entity.UserAuthorities;
import startline.server.repository.UserAuthoritiesRepositoryInterface;
import startline.server.repository.UserRepositoryInterface;
import startline.server.token.TokenGenerator;
import startline.server.token.TokenService;
import startline.server.user.AbstractAuthnProvider;
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
    private final UserAuthoritiesRepositoryInterface userAuthoritiesRepository;
    public TokenDTO login(MemberRequestDTO requestBody, HttpServletRequest request) throws Exception {
        UsernamePasswordAuthenticationToken primaryAuthentication = requestBody.toAuthentication();
        Authentication auth = abstractAuthnProvider.authenticate(primaryAuthentication);

        long refreshTokenExpiresIn = Long.parseLong(request.getHeader("refreshTokenExpiresIn"));
        String refreshToken = request.getHeader("RefreshToken");

        //refreshToken이 없을 경우, access token과 refresh token을 모두 생성한다.
        if(refreshToken == null)
            return generator.generateTokens(auth);
        if(refreshToken != null && tokenService.validateRefreshToken(refreshToken) && tokenService.checkExpireTime(refreshTokenExpiresIn))
            return generator.generateTokens(auth);
        if(refreshToken != null && tokenService.validateRefreshToken(refreshToken) && !tokenService.checkExpireTime(refreshTokenExpiresIn))
            return generator.generateAccessToken(auth);



        throw new RuntimeException("로그인 실패! location = SignService.login");
    }
    public void signup(MemberRequestDTO requestBody) {
        User user = requestBody.signup(passwordEncoder);
        userRepository.save(user);

        setAuthoritiyForSignUp(requestBody.getUsername());
    }
    private void setAuthoritiyForSignUp(String username) {
        UserAuthorities userAuth = new UserAuthorities(username, AuthorityName.ROLE_PRE);
        userAuthoritiesRepository.save(userAuth);
    }
}
