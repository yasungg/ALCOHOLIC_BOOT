package ALCOHOLIC_BOOT.server.repository;

import ALCOHOLIC_BOOT.server.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Ref;

@Repository
public interface RefreshTokenRepositoryInterface extends JpaRepository<RefreshToken, String> {
    RefreshToken findByToken(String RefreshToken);
}
