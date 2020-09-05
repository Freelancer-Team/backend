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

    @Autowired
    private UserService userService;

    @Test
    public void getUsers() {
        int num = 1221;
        assertEquals(num,userService.getUsers().size());
    }

    @Test
    public void login() {
        int id = 1;
        assertEquals(id,userService.login("dkfyfvgu@163.com","536").getId());
    }

    @Test
    public void signup(){
        userService.signup("fortest","12345","1234577@qq.com","dongchuan","123456789");
        assertEquals(userService.findUserByemail("1234577@qq.com").getName(),"fortest");
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
    public void getUser() {
        String email = "xuxmsm@163.com";
        assertEquals(email, userService.getUser(2).getEmail());
    }

    @Test
    public void saveUser() {
        User user = userService.getUser(2);
        user.setRole((user.getRole()+1)%2);
        assertEquals(user,userService.saveUser(user));
    }

    @Test
    public void updateSkills() {
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
    public void getToken(){
        User user = userService.getUser(1);
        assertEquals("Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJka2Z5ZnZndUAxNjMuY29tIn0.76oqMD69bG04gWaEMyZDxJj56MPTDNAXsDZEDGAgO8k",userService.getToken(user));
    }

    @Test
    public void findUserByemail(){
        User user = userService.getUser(1);
        assertEquals(user.getName(),userService.findUserByemail("dkfyfvgu@163.com").getName());
    }

    @Test
    void setUserRole(){
        userService.setUserRole(1,1);
        assertEquals(1,userService.getUser(1).getRole());
    }

    @Test
    void setShow(){
        int show = userService.getUser(1).getIsShow();
        userService.setShow(1);
        assertEquals(show==1?0:1,userService.getUser(1).getIsShow());
    }

    @Test
    void updateUserInfo(){
        User user = userService.getUser(1);
        userService.updateUserInfo(1,user.getName(),user.getGender(),user.getAge()+1,user.getAddress(),user.getPhone(),user.getDescription(),user.getIcon());
        assertEquals(user.getAge()+1,userService.getUser(1).getAge());
    }
}
