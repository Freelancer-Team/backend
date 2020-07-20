package freelancer.service;

import freelancer.FreelancerApplicationTest;
import freelancer.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest extends FreelancerApplicationTest {

    @Test
    public void contextLoads() {
    }

    @Autowired
    private UserService userService;

    @Test
    public void getUsers() {
        int num = 1217;
        assertEquals(num,userService.getUsers().size());
    }

    @Test
    public void login() {
        int id = 5;
        assertEquals(id,userService.login("vpdtdaojyg@163.com","60852").getId());
    }

    @Test
    void updateSkills() {
        List<String> skills = new ArrayList<String>();;
        skills.add("Hair Styles");
        skills.add("SVG");
        skills.add("A/V editing");
        skills.add("Chef Configuration Management");
        skills.add("Immigration");
        skills.add("Article Writing");
        assertEquals(skills,userService.updateSkills(skills,1).getSkills());
    }

    @Test
    public void getUserSkills() {
        List<String> skills = new ArrayList<String>();;
        skills.add("Hair Styles");
        skills.add("SVG");
        skills.add("A/V editing");
        skills.add("Chef Configuration Management");
        skills.add("Immigration");
        skills.add("Article Writing");
        assertEquals(skills, userService.getUserSkills(1));
    }

    @Test
    void getUser() {
        String email = "xuxmsm@163.com";
        assertEquals(email, userService.getUser(2).getEmail());
    }

    @Test
    void saveUser() {
        User user = userService.getUser(2);
        user.setRole((user.getRole()+1)%2);
        assertEquals(user,userService.saveUser(user));
    }

}
