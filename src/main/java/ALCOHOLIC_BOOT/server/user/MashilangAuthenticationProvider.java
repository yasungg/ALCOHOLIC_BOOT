package ALCOHOLIC_BOOT.server.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component("MashilangAuthenticationProviderManager")
@Slf4j
public class MashilangAuthenticationProvider implements AuthenticationProvider {
    private final AbstractAuthnProvider abstractAuthnProvider;
    private final MashilangUserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = (String) authentication.getPrincipal();
        MashilangUserDetails userDetails = userDetailsService.loadUserByUsername(username);

        abstractAuthnProvider.additionalAuthenticationChecks(userDetails, (UsernamePasswordAuthenticationToken) authentication);

        return new UsernamePasswordAuthenticationToken(username, null, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
