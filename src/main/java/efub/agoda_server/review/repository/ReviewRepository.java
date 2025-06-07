package efub.agoda_server.review.repository;

import efub.agoda_server.reservation.domain.Reservation;
import efub.agoda_server.review.domain.Review;
import efub.agoda_server.stay.domain.Stay;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    boolean existsByReservation(Reservation reservation);
    List<Review> findAllByStay(Stay stay);
    Review findByReservation(Reservation reservation);
}
