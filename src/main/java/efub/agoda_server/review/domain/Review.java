package efub.agoda_server.review.domain;

import efub.agoda_server.reservation.domain.Reservation;
import efub.agoda_server.stay.domain.Stay;
import efub.agoda_server.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name="review")
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(toBuilder = true)
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long revId;

    @Column(nullable = false)
    private int addrRating;

    @Column(nullable = false)
    private int saniRating;

    @Column(nullable = false)
    private int servRating;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Column(columnDefinition = "TEXT")
    private String text;

    @OneToOne
    @JoinColumn(name="res_id", nullable = false, unique = true)
    private Reservation reservation;

    @ManyToOne
    @JoinColumn(name = "stay_id", nullable = false)
    private Stay stay;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    public void updateAll(int addrRating, int saniRating, int servRating, String reviewText, LocalDateTime updatedAt) {
        this.addrRating = addrRating;
        this.saniRating = saniRating;
        this.servRating = servRating;
        this.text = reviewText;
        this.updatedAt = updatedAt;
    }

    //review 수정 시 rating 복사
    public static Review copyRatingsFrom(Review review) {
        return new Review(
                review.getAddrRating(),
                review.getSaniRating(),
                review.getServRating(),
                review.getReservation() // stay 정보용
        );
    }

    public Review(int addrRating, int saniRating, int servRating, Reservation reservation) {
        this.addrRating = addrRating;
        this.saniRating = saniRating;
        this.servRating = servRating;
        this.reservation = reservation;
    }
}
