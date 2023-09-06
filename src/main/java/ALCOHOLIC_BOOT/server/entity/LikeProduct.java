package ALCOHOLIC_BOOT.server.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;

@Entity
@Getter @Setter @ToString
@Table(name = "m_like_product")
@DynamicInsert
public class LikeProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_product_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "username")
    @NotNull
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    @NotNull
    private Product product;

    @Column(name = "enlisted_time")
    @CreationTimestamp
    private LocalDateTime enlistedTime;
}
