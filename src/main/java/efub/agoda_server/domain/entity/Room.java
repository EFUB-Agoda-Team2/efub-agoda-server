package efub.agoda_server.domain.entity;

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
    private String name; // 슈페리어 트윈으로 고정

    @Column(name = "room_image", nullable = false)
    private String roomImage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "st_id", nullable = false)
    private Stay stay;
}
