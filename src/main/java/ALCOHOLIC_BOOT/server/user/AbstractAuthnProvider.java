package ALCOHOLIC_BOOT.server.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.*;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component("AbstractAuthnProvider")
@RequiredArgsConstructor
@Slf4j
public class AbstractAuthnProvider extends AbstractUserDetailsAuthenticationProvider {
    private final PasswordEncoder passwordEncoder;
    private final MashilangUserDetailsService userDetailsService;
    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        String password = (String) authentication.getCredentials();
        String dbPassword = userDetails.getPassword();

        if(userDetails == null || !passwordEncoder.matches(password, dbPassword)) {
            throw new BadCredentialsException("비밀번호가 틀렸습니다!");
        }
        if(!userDetails.isAccountNonLocked()) {            //계정이 잠긴 경우(true로 고정, 추후 옵션처리 가능)
            log.info("location: PerfestAuthenticationProvider, LockedException");
            throw new LockedException("차단된 회원입니다.");
        }
        if(!userDetails.isEnabled()) {                    //계정이 비활성화된 경우
            log.info("location: PerfestAuthenticationProvider, DisabledException");
            throw new DisabledException("탈퇴한 회원입니다.");
        }
        if(!userDetails.isAccountNonExpired()) {			//계정이 만료된 경우
            log.info("location: PerfestAuthenticationProvider, AccountExpiredException");
            throw new AccountExpiredException("만료된 계정입니다.");
        }
        if(!userDetails.isCredentialsNonExpired()) {		//비밀번호가 만료된 경우
            log.info("location: PerfestAuthenticationProvider, CredentialsExpiredException");
            throw new CredentialsExpiredException("만료된 비밀번호입니다.");
        }
    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        return null;
    }
}
