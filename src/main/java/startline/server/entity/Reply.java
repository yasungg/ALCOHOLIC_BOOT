package startline.server.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;

@Entity
@Getter @Setter @ToString
@AllArgsConstructor
@Table(name = "s_reply")
public class Reply {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reply_id")
    private Long id;

    @Column(name = "reply", length = 1200)
    @NotNull
    private String reply_content;

    @CreationTimestamp
    @Column(name = "reply_time")
    LocalDateTime replyWrittenTime;

    @ManyToOne
    @JoinColumn(name = "board_id")
    @NotNull
    private Board board;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "username")
    @NotNull
    private User user;
}
