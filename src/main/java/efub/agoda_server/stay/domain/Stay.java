package efub.agoda_server.stay.domain;

import efub.agoda_server.converter.TagsConverter;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;

@Entity
@Table(name = "stay")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder(toBuilder = true)
public class Stay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long stId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false, name = "sale_price")
    private int salePrice;

    @Column(columnDefinition = "TEXT")
    @Convert(converter = TagsConverter.class)
    private List<String> tags;

    @Column(nullable = false)
    private String detail;

    // 전체 평균 별점
    @Column(name = "rating")
    private double rating;

    // 항목별 평균 별점
    @Column(name = "addr_rating")
    private double addrRating;

    @Column(name = "sani_rating")
    private double saniRating;

    @Column(name = "serv_rating")
    private double servRating;

    // 총 리뷰 수
    @Column(nullable = false, name = "review_cnt")
    @ColumnDefault("0")
    private int reviewCnt;

    public void updateReview(double newAddrRating, double newSaniRating, double newServRating, double newRating) {
        this.rating = newRating;
        this.addrRating = newAddrRating;
        this.saniRating = newSaniRating;
        this.servRating = newServRating;
        this.reviewCnt ++;
    }
}