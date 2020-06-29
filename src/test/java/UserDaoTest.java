import com.toby.web.dao.UserDao;
import com.toby.web.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.SQLException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/spring/applicationContext.xml")
@DirtiesContext
public class UserDaoTest {
    // 테스트 오브젝트가 만들어지고 나면 스프링 테스트 컨텍스트에 의해 자동으로 값이 주입.
    @Autowired
    UserDao dao;

    private User user1;
    private User user2;
    private User user3;

    @Before
    public void setUp(){
        DataSource dataSource = new SingleConnectionDataSource(
                "jdbc:mysql://localhost/edu_toby_spring","root","cs1234",true
        );
        user1 = new User("user", "kyeong", "hihi");
        user2 = new User("user1", "kyeong2", "hihi");
        user3 = new User("user2", "kyeong3", "hihi");

    }

    // JUnit에게 테스트용 메소드임을 알려준다.
    @Test
    public void addAndGet() throws SQLException, ClassNotFoundException {
        dao.deleteAll();
        assertThat(dao.getCount(), is(0));

        dao.add(user1);
        assertThat(dao.getCount(), is(1));

        dao.add(user2);
        assertThat(dao.getCount(), is(2));

        dao.add(user3);
        assertThat(dao.getCount(), is(3));
    }
}
