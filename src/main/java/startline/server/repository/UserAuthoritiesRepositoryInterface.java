package startline.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import startline.server.constant.AuthorityName;
import startline.server.entity.UserAuthorities;

@Repository
public interface UserAuthoritiesRepositoryInterface extends JpaRepository<UserAuthorities, String> {
    @Modifying
    @Transactional
    @Query("DELETE FROM UserAuthorities WHERE username = :username AND authority = :authority")
    void deleteByUsernameAndAuthority(@Param("username") String username, @Param("authority") AuthorityName authority);

    @Query("SELECT CASE WHEN COUNT(ua) > 0 THEN true ELSE false END FROM UserAuthorities ua WHERE ua.username = :username AND ua.authority = :authority")
    boolean existsByUsernameAndAuthority(String username, AuthorityName authority);
}
