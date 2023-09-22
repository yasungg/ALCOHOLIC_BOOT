package startline.server.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.jetbrains.annotations.NotNull;
import startline.server.constant.Category;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter @Setter @ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "s_board")
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    @Column(name = "board_title", length = 100)
    @NotNull
    private String boardTitle;

    @Column(name = "board_content", length = 6000)
    @NotNull
    private String boardContent;

    @ManyToOne(cascade = CascadeType.ALL)
    @NotNull
    private User user;

    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
    private List<Reply> reply;

    @Enumerated(EnumType.STRING)
    @Column(name = "board_category", length = 20)
    @NotNull
    private Category category;

    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
    private List<BoardImage> boardImage;

    @CreationTimestamp
    @Column(name = "board_time")
    private LocalDateTime boardWrittenTime;



}
