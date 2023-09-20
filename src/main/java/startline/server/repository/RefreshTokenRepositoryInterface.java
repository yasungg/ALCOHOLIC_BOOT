package startline.server.repository;

import startline.server.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepositoryInterface extends JpaRepository<RefreshToken, String> {
    RefreshToken findByToken(String RefreshToken);
}
