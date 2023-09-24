package startline.server.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import startline.server.constant.AuthorityName;
import startline.server.entity.UserAuthorities;
import startline.server.repository.UserAuthoritiesRepositoryInterface;
import startline.server.token.TokenService;

import java.util.*;
import java.util.stream.Collectors;

import static startline.server.constant.AuthorityName.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminService {
    private final UserAuthoritiesRepositoryInterface userAuthoritiesRepository;
    private final TokenService tokenService;

    @Transactional
    public void addAuthorities(String username, AuthorityName authority) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(username != auth.getName()) throw new UsernameNotFoundException("회원 이메일이 일치하지 않습니다.");

        Set<AuthorityName> authListForAddDB = new HashSet<>();

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(auth.getAuthorities()
                        .toString()
                        .split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());


        if(authority.equals(ROLE_ADMIN) && !authorities.contains(ROLE_USER)) {
            authListForAddDB.add(ROLE_ADMIN);
            authListForAddDB.add(ROLE_USER);
        }
        if(authority.equals(ROLE_DOCTOR) && !authorities.contains(ROLE_USER)) {
            authListForAddDB.add(ROLE_DOCTOR);
            authListForAddDB.add(ROLE_USER);
        }
        if(authority.equals(ROLE_ADMIN) && authorities.contains(ROLE_USER)) {
            authListForAddDB.add(ROLE_ADMIN);
        }
        if(authority.equals(ROLE_DOCTOR) && authorities.contains(ROLE_USER)) {
            authListForAddDB.add(ROLE_DOCTOR);
        }

        UserAuthorities userAuth = UserAuthorities.builder()
                        .username(username)
                                .authorityName(authListForAddDB)
                                        .build();

        userAuthoritiesRepository.save(userAuth);
    }
    public void deleteAuthorities(String username, AuthorityName authority) {

    }
}
