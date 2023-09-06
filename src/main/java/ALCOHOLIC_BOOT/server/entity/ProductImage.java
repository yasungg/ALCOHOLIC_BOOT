package ALCOHOLIC_BOOT.server.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.jetbrains.annotations.NotNull;

@Entity
@Getter @Setter @ToString
@Table(name = "m_product_img")
public class ProductImage {
    @Id
    @Column(name = "product_img_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "p_img_link", length = 500)
    @NotNull
    private String productImgLink;
}
