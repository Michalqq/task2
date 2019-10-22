package michal.task2;

import michal.task2.service.UserSaver;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.time.LocalDate;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

//
//    @Autowired
//    private UserSaver userSaver;
//
//    @Test
//    public void saveTest(){
//        LocalDate birthdate = LocalDate.of(1993,06,05);
//        userSaver.addNew("Msdd", "krr",birthdate, 500150150);
//    }
}
