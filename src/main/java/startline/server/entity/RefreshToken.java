package startline.server.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

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
    @JoinColumn(name = "username")
    @NotNull
    private User user;
}
