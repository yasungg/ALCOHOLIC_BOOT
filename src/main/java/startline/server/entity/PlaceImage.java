package startline.server.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter @ToString
@Table(name = "s_place_img")
public class PlaceImage {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "place_img_id")
    private Long id;

    @Column(name = "place_img_link", length = 500)
    private String placeImgLink;

    @JoinColumn(name = "place_id")
    private Place place;
}
