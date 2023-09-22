package startline.server.entity;

import jakarta.persistence.*;
import lombok.*;
import org.jetbrains.annotations.NotNull;

import java.sql.Ref;

@Getter @Setter @ToString
@Entity
@AllArgsConstructor
@Table(name = "s_refresh_token")
public class RefreshToken {
    @Id
    @Column(name = "token_value", length = 500)
    @NotNull
    private String tokenValue;

    @Column(name = "token_expires_in")
    @NotNull
    private Long tokenExpiresIn;

    @OneToOne
//    @JoinColumn(name = "username")
    @NotNull
    private User user;

    @Builder
    public RefreshToken(String tokenValue, Long tokenExpiresIn, String username) {
        this.tokenValue = tokenValue;
        this.tokenExpiresIn = tokenExpiresIn;
        this.user = new User(username);
    }
}
