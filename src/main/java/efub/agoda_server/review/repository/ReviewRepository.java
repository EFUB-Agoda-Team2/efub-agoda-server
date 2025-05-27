package efub.agoda_server.review.repository;

import efub.agoda_server.review.domain.Review;
import efub.agoda_server.stay.domain.Stay;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findAllByStay(Stay stay);
}
