package startline.server.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter @ToString
@Table(name = "s_authority")
@AllArgsConstructor
@NoArgsConstructor
public class Authority {
    @Id
    @Enumerated(EnumType.STRING)
    @Column(name = "authority_name", length = 20)
    private Authority authorityName;
}
