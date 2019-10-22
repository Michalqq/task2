package michal.task2.repository;

import michal.task2.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT DISTINCT fileName FROM User")
    List<String> fileNameList();

    @Query("FROM User WHERE fileName = :fileName")
    List<User> findUsersWhereFileNameIs(String fileName);

    @Query("FROM User WHERE age = (SELECT COALESCE(MAX(age),0) FROM User WHERE phoneNumber > 0)")
    List<User> findTheOldestUsersWithPhoneNumber();


}
