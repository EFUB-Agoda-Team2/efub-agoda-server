package efub.agoda_server.stay.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "room")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder(toBuilder = true)
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String bed;

    @Column(nullable = false)
    private int price;

    @Column(name = "sale_price", nullable = false)
    private int salePrice;

    @Column(name = "room_image", nullable = false)
    private String roomImage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "st_id", nullable = false)
    private Stay stay;
}
