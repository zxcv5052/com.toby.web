import com.toby.web.dao.UserDao;
import com.toby.web.domain.User;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.SQLException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class UserDaoTest_Spring {
    // JUnit에게 테스트용 메소드임을 알려준다.
    @Test
    public void addAndGet() throws SQLException, ClassNotFoundException {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring/applicationContext.xml");

        UserDao dao = context.getBean("userDao", UserDao.class);
        User user = new User();
        user.setId("user");
        user.setName("박형서");
        user.setPassword("hihi");

        dao.add(user);

        User user2 = dao.get(user.getId());
        // if/else 문장을 Junit은 AssertThat이라는 스태틱 메소드를 이용해 변경한다.
        // is() 는 일종의 equals() 로 비교해주는 기능을 가졌다.
        assertThat(user2.getName(), is(user.getName()));
        assertThat(user2.getPassword(),is(user.getPassword()));
    }
}
