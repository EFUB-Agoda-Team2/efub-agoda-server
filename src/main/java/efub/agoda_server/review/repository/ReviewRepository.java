package efub.agoda_server.review.repository;

import efub.agoda_server.reservation.domain.Reservation;
import efub.agoda_server.review.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    boolean existsByReservation(Reservation reservation);
}
