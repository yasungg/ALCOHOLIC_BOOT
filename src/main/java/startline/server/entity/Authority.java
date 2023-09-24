package startline.server.entity;

import jakarta.persistence.*;
import lombok.*;
import org.jetbrains.annotations.NotNull;
import startline.server.constant.AuthorityName;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter @Setter @ToString
@Table(name = "s_authority")
@AllArgsConstructor
@NoArgsConstructor
public class Authority {
    @Id
    @Enumerated(EnumType.STRING)
    @Column(name = "authority_name", length = 20)
    private AuthorityName authorityName;

    @OneToMany(mappedBy = "authority", cascade = CascadeType.PERSIST)
    @NotNull
    private Set<UserAuthorities> authorities = new HashSet<>();

    public Authority(AuthorityName authorityName) {
        this.authorityName = authorityName;
    }
}
