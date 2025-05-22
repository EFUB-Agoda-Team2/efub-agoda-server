package efub.agoda_server.review.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="review_img")
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(toBuilder = true)
public class ReviewImg {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long revImgId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="rev_id",nullable = false)
    private Review review;

    @Column(name ="rev_image",nullable = false)
    private String revImage;
}
