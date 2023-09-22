package startline.server.entity;

import jakarta.persistence.*;
import lombok.*;
import startline.server.constant.AuthorityName;

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
    private Authority authority;

    @Builder
    public UserAuthorities(String username, AuthorityName authorityName) {
        this.user = new User(username);
        this.authority = new Authority(authorityName);
    }
}
