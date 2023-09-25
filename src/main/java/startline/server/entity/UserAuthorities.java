package startline.server.entity;

import jakarta.persistence.*;
import lombok.*;
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
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_authorities_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "username")
    private User user;

    @ManyToOne
    @JoinColumn(name = "authority_name")
    private Set<Authority> authority;

    @Builder
    public UserAuthorities(String username, Set<AuthorityName> authorityName) {
        this.user = new User(username);
        this.authority = authorityName.stream()
                .map(Authority::new)
                .collect(Collectors.toSet());
    }
}
