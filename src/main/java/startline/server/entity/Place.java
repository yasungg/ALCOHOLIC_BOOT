package startline.server.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter @Setter @ToString
@Table(name = "s_place")
public class Place {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "place_id")
    private Long id;

    @Column(name = "place_name", length = 60)
    private String placeName;

    @Column(name = "place_desc", length = 4000)
    private String placeDesc;

    @Column(name = "place_phone", length = 20)
    private String placePhone;

    @OneToMany(mappedBy = "place", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JoinColumn(name = "place_img_id")
    private List<PlaceImage> placeImage;

    @Column(name = "place_address", length = 100)
    private String address;

    @Column(name = "place_doro", length = 100)
    private String doro;

    @Column(name = "latitude", length = 40)
    private String latitude;

    @Column(name = "longitude", length = 40)
    private String longitude;
}
