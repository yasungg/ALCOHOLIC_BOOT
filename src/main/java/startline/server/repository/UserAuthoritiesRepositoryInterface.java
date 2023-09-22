package startline.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import startline.server.entity.UserAuthorities;

public interface UserAuthoritiesRepositoryInterface extends JpaRepository<UserAuthorities, Long> {

}
