package startline.server.entity;

import startline.server.constant.Authority;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;

@Entity
@Getter @Setter @ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "m_user")
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

    @Enumerated(EnumType.STRING)
    @Column(name = "authority", length = 15)
    @NotNull
    private Authority authority;

    @Column(name = "join_time")
    @CreationTimestamp
    private LocalDateTime joinTime;

    @Column(name = "is_enabled", columnDefinition = "TINYINT(1)")
    @ColumnDefault("true")
    private boolean isEnabled;

    @Builder
    public User(String username, String password, String name, String nickname, String phone, Authority authority) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.phone = phone;
        this.authority = authority;
    }

    public User(String subject, String password, String nickname, Authority authority) {
        this.username = subject;
        this.password = password;
        this.nickname = nickname;
        this.authority = authority;
    }
}
