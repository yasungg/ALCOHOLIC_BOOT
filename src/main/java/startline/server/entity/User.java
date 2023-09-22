package startline.server.entity;

import startline.server.constant.AuthorityName;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter @Setter @ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "s_user")
public class User {
    @Id
    @Column(name = "username", length = 30)
    private String username;

    @Column(name = "password", length = 200)
    @NotNull
    private String password;

    @Column(name = "name", length = 30)
    @NotNull
    private String name;

    @Column(name = "nickname", length = 20)
    @NotNull
    private String nickname;

    @Column(name = "phone", length = 15)
    private String phone;

    @Column(name = "profile_img", length = 500)
    private String profileImg;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @NotNull
    private Set<UserAuthorities> authorities = new HashSet<>();

    @OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY) // mappedBy = 연관관계의 주인이 아니다. 이 컬럼은 FK가 아니다. FK의 관리 역할은 상대 쪽에 있다.
    private RefreshToken refreshToken;

    @Column(name = "join_time")
    @CreationTimestamp
    private LocalDateTime joinTime;

    @Column(name = "is_enabled", columnDefinition = "TINYINT(1)")
    @ColumnDefault("true")
    private boolean isEnabled;

    @Column(name = "is_account_non_locked", columnDefinition = "TINYINT(1)")
    @ColumnDefault("true")
    private boolean isAccountNonLocked;

    @Builder
    public User(String username, String password, String name, String nickname, String phone) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.phone = phone;
    }
    public User(String username) {
        this.username = username;
    }
    public User(String subject, String password, String nickname) {
        this.username = subject;
        this.password = password;
        this.nickname = nickname;
    }
}
