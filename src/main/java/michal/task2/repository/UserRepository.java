package michal.task2.repository;

import michal.task2.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    //void delete(Optional<User> byId);
    @Query("SELECT COALESCE(MAX(listNr),0) FROM User")
    Integer findMaxListNr();

    @Query("SELECT DISTINCT fileName FROM User")
    List<String> fileNameList();
}
