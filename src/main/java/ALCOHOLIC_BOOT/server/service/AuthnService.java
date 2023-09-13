package ALCOHOLIC_BOOT.server.service;

import ALCOHOLIC_BOOT.server.dto.MemberRequestDTO;
import ALCOHOLIC_BOOT.server.dto.TokenDTO;
import ALCOHOLIC_BOOT.server.exceptions.UserExistException;
import ALCOHOLIC_BOOT.server.repository.UserRepository;
import ALCOHOLIC_BOOT.server.token.TokenGenerator;
import ALCOHOLIC_BOOT.server.user.AbstractAuthnProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthnService {
    private final UserRepository userRepository;
    private final AbstractAuthnProvider abstractAuthnProvider;
    private final TokenGenerator generator;
    public TokenDTO login(MemberRequestDTO requestBody, HttpServletRequest request) throws Exception {
        UsernamePasswordAuthenticationToken primaryAuthentication = requestBody.toAuthentication();
        Authentication auth = abstractAuthnProvider.authenticate(primaryAuthentication);

        if(request.getHeader("RefreshToken") == null) return generator.generateToken(auth);




    }
}
