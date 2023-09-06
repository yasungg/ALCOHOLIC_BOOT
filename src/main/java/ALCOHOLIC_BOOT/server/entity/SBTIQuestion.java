package ALCOHOLIC_BOOT.server.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

@Entity
@Getter @Setter @ToString
@Table(name = "m_sbti_question")
public class SBTIQuestion {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sbti_question_id")
    private Long id;

    @Column(name = "question", length = 100)
    @NotNull
    private String question;

}
