package ALCOHOLIC_BOOT.server.user;

import ALCOHOLIC_BOOT.server.entity.User;
import ALCOHOLIC_BOOT.server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class MashilangUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public MashilangUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(userRepository.findByUsername(username).isPresent()) {
            User user = userRepository.findByUsername(username).get();
            return new MashilangUserDetails(user);
        }
        throw new UsernameNotFoundException("사용자를 찾을 수 없습니다");
    }
}
