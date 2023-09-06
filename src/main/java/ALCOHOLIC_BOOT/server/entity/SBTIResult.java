package ALCOHOLIC_BOOT.server.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

@Entity
@Getter @Setter @ToString
@Table(name = "m_sbti_result")
public class SBTIResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sbti_result_id")
    private Long id;

    @Column(name = "result_category", length = 60)
    @NotNull
    private String resultCategory;

    @Column(name = "result_description")
    @Lob @NotNull
    private String resultDesc;
}
