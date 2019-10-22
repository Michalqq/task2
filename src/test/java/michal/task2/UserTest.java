package michal.task2;

import michal.task2.model.User;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;

public class UserTest {

    @Test
    public void testAgeCounter() {
        LocalDate dateOfBirth = LocalDate.now().minusYears(10);
        User user = new User("Adam", "Kowalski", dateOfBirth, 500150150);
        Assert.assertTrue(user.getAge() == 10);
    }
}
