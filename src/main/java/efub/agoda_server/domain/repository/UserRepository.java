package efub.agoda_server.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import efub.agoda_server.domain.entity.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
