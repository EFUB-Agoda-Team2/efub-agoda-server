package efub.agoda_server.stay.repository;

import efub.agoda_server.stay.domain.Stay;
import efub.agoda_server.stay.domain.StayImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StayImageRepository extends JpaRepository<StayImage, Long> {
    StayImage findFirstByStay(Stay stay);
    List<StayImage> findByStay(Stay stay);
}
