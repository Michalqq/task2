package michal.task2.repository;

import michal.task2.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    //void delete(Optional<User> byId);
}
