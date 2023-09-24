package startline.server.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

@Entity
@Getter @Setter @ToString
@Table(name = "s_board_img")
public class BoardImage {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_img_id")
    @NotNull
    private Long id;

    @Column(name = "board_img_link", length = 500)
    private String boardImgLink;

    @ManyToOne
    @NotNull
    private Board board;
}
