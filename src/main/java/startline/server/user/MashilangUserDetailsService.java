package startline.server.user;

import startline.server.entity.User;
import startline.server.repository.UserRepositoryInterface;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class MashilangUserDetailsService implements UserDetailsService {
    private final UserRepositoryInterface userRepositoryInterface;
    @Override
    public MashilangUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            User user = userRepositoryInterface.findByUsername(username);
            return new MashilangUserDetails(user);
    }
}
