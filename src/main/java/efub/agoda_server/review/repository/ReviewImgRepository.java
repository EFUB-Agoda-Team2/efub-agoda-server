package efub.agoda_server.review.repository;

import efub.agoda_server.review.domain.Review;
import efub.agoda_server.review.domain.ReviewImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewImgRepository extends JpaRepository<ReviewImg, Long> {
     List<ReviewImg> findAllByReview(Review review);
     Void deleteAllByReview(Review review);
}
