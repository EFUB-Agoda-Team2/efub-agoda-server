package efub.agoda_server.reservation.repository;

import efub.agoda_server.reservation.domain.Reservation;
import efub.agoda_server.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findAllByUser(User user);

}
