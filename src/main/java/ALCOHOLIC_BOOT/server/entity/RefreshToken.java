package ALCOHOLIC_BOOT.server.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
@Entity
@AllArgsConstructor
@Table(name = "m_refresh_token")
public class RefreshToken {
    @Id
    @Column(name = "token", length = 500)
    private String token;
}
