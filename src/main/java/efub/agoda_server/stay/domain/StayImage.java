package efub.agoda_server.stay.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "stay_image")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder(toBuilder = true)
public class StayImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stImgId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "st_id", nullable = false)
    private Stay stay;

    @Column(name = "st_image", nullable = false)
    private String stImage;
}
