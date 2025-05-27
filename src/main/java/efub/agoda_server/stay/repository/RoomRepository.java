package efub.agoda_server.stay.repository;

import efub.agoda_server.stay.domain.Room;
import efub.agoda_server.stay.domain.Stay;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findAllByStay(Stay stay);
}