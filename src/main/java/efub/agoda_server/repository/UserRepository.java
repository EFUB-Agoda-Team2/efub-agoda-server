package efub.agoda_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import efub.agoda_server.entity.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
