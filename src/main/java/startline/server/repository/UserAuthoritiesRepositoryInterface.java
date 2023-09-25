package startline.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import startline.server.constant.AuthorityName;
import startline.server.entity.UserAuthorities;

public interface UserAuthoritiesRepositoryInterface extends JpaRepository<UserAuthorities, Long> {
    void deleteByUsernameAndAuthority(String username, AuthorityName authority);

    boolean existsByUsernameAndAuthority(String username, AuthorityName authority);
}
