package efub.agoda_server.domain.entity;

import efub.agoda_server.converter.TagsConverter;
import jakarta.persistence.*;
import lombok.*;

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
    private String city;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false, name = "sale_price")
    private int salePrice;

    @Column(nullable = false)
    private double rating;

    @Column(columnDefinition = "TEXT")
    @Convert(converter = TagsConverter.class)
    private List<String> tags;

    @Column(nullable = false)
    private String detail;
}