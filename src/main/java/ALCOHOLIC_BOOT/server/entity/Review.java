package ALCOHOLIC_BOOT.server.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@Table(name = "m_review")
public class Review {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "username")
    private User user;

    @Column(name = "review_title", length = 128)
    @NotNull
    private String reviewTitle;

    @Column(name = "review_description")
    @NotNull @Lob
    private String reviewDescription;

    @Column(name = "written_time")
    @CreationTimestamp
    private LocalDateTime writtenTime;
}
