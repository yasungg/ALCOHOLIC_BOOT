package startline.server.entity;

import jakarta.persistence.*;
import lombok.*;
import org.jetbrains.annotations.NotNull;
import startline.server.constant.AuthorityName;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter @Setter @ToString
@AllArgsConstructor
@Table(name = "s_user_authorities")
public class UserAuthorities {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_authorities_id")
    private Long id;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "username", referencedColumnName = "username")
    @NotNull
    private User username;

    @Column(name = "authority")
    @Enumerated(EnumType.STRING)
    @NotNull
    private AuthorityName authority;

    @Builder
    public UserAuthorities(String username, AuthorityName authority) {
        this.username = new User(username);
        this.authority = authority;
    }
}
