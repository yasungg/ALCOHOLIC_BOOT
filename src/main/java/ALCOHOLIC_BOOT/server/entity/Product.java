package ALCOHOLIC_BOOT.server.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

@Entity
@Getter @Setter @ToString
@Table(name = "m_product")
public class Product {
    @Id
    @Column(name = "product_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_name", length = 40, unique = true)
    @NotNull
    private String productName;

    @Column(name = "alcohol_percentage", length = 10)
    private String alcoholPercentage;

    @Column(name = "category", length = 40)
    @NotNull
    private String category;

    @Column(name = "capacity", length = 10)
    @NotNull
    private String capacity;

    @Column(name = "store_link", length = 500)
    private String storeLink;

    @Column(name = "description_img", length = 500)
    private String descriptionImg;

    @Column(name = "theme", length = 15)
    @NotNull
    private String theme;

}
