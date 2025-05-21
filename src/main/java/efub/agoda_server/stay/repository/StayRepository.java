package efub.agoda_server.stay.repository;


import efub.agoda_server.stay.domain.Stay;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StayRepository extends JpaRepository<Stay, Long> {
    Page<Stay> findBySalePriceBetweenAndCity(int minPrice, int maxPrice, String city, Pageable pageable);
}
