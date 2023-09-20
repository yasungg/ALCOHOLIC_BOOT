package startline.server.repository;

import startline.server.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepositoryInterface extends JpaRepository<User, String> {
    Optional<User> findByUsername(String username);
    Optional<String> findUsernameByUsername(String usename);
}
