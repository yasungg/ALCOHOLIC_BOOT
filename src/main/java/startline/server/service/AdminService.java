package startline.server.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import startline.server.constant.AuthorityName;
import startline.server.entity.UserAuthorities;
import startline.server.repository.UserAuthoritiesRepositoryInterface;
import startline.server.repository.UserRepositoryInterface;

import java.util.*;
import java.util.stream.Collectors;

import static startline.server.constant.AuthorityName.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminService {
    private final UserAuthoritiesRepositoryInterface userAuthoritiesRepository;
    private final UserRepositoryInterface userRepository;

//    @Transactional(isolation = Isolation.SERIALIZABLE)
//    public void addAuthorities(String username, AuthorityName authority) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if(!username.equals(auth.getName())) throw new UsernameNotFoundException("회원 이메일이 일치하지 않습니다.");
//
//        Set<AuthorityName> authListForAddDB = new HashSet<>();
//
//        Collection<? extends GrantedAuthority> authorities =
//                Arrays.stream(auth.getAuthorities()
//                        .toString()
//                        .split(","))
//                        .map(SimpleGrantedAuthority::new)
//                        .collect(Collectors.toList());
//
//        if(authority.equals(ROLE_ADMIN) && !authorities.contains(ROLE_USER)) {
//            authListForAddDB.add(ROLE_ADMIN);
//            authListForAddDB.add(ROLE_USER);
//        }
//        if(authority.equals(ROLE_ADMIN) && authorities.contains(ROLE_USER)) {
//            authListForAddDB.add(ROLE_ADMIN);
//        }
//        if(authority.equals(ROLE_DOCTOR) && !authorities.contains(ROLE_USER)) {
//            authListForAddDB.add(ROLE_DOCTOR);
//            authListForAddDB.add(ROLE_USER);
//        }
//        if(authority.equals(ROLE_DOCTOR) && authorities.contains(ROLE_USER)) {
//            authListForAddDB.add(ROLE_DOCTOR);
//        }
//
//        UserAuthorities userAuth = UserAuthorities
//                .builder()
//                .username(username)
//                .authorityName(authListForAddDB)
//                .build();
//
//        userAuthoritiesRepository.save(userAuth);
//    }
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void deleteAuthorities(String username, AuthorityName authority) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(!username.equals(auth.getName())) throw new UsernameNotFoundException("회원 이메일이 일치하지 않습니다.");
        if(!userAuthoritiesRepository.existsByUsernameAndAuthority(username, authority)) {
            throw new RuntimeException("해당 회원은 지정된 권한을 가지고 있지 않습니다!");
        }

        userAuthoritiesRepository.deleteByUsernameAndAuthority(username, authority);
    }
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void changeUserEnableStatus(String username, boolean status) {
        if(!username.equals(userRepository.findUsernameByUsername(username)))
            throw new UsernameNotFoundException("해당 회원은 존재하지 않습니다!");
        if(userRepository.checkUserEnableStatus(username) == status)
            throw new RuntimeException("해당 사용자의 상태를" + status + "로 바꿀 수 없습니다.");

        userRepository.changeUserEnableStatus(username, status);
    }
}
