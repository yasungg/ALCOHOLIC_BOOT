package startline.server.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component("MashilangAuthenticationProviderManager")
@Slf4j
public class MashilangAuthenticationProvider implements AuthenticationProvider {
    private final AbstractAuthnProvider abstractAuthnProvider;
    private final MashilangUserDetailsService userDetailsService;
    private GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = (String) authentication.getPrincipal();
        UserDetails userDetails = abstractAuthnProvider.retrieveUser(username, (UsernamePasswordAuthenticationToken) authentication);
        log.info("리턴 = {}", userDetails);
        abstractAuthnProvider.additionalAuthenticationChecks(userDetails, (UsernamePasswordAuthenticationToken) authentication);
        return createSuccessAuthentication(username, authentication, userDetails);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

    protected Authentication createSuccessAuthentication(Object principal, Authentication authentication,
                                                         UserDetails user) {

        UsernamePasswordAuthenticationToken result = UsernamePasswordAuthenticationToken.authenticated(principal,
                authentication.getCredentials(), this.authoritiesMapper.mapAuthorities(user.getAuthorities()));
        result.setDetails(authentication.getDetails());
        return result;
    }
}
