package freelancer.controller;

import freelancer.entity.Skill;
import freelancer.entity.User;
import freelancer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/getUsers")
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @RequestMapping("/login")
    public User login(@RequestParam("email") String email,@RequestParam("password") String password){
        return userService.login(email, password);
    }

    @RequestMapping("/signup")
    public User signup(@RequestParam("name") String name,@RequestParam("password") String password,
                       @RequestParam("email") String email,@RequestParam("address") String address,
                       @RequestParam("phone") String phone){
        return userService.signup(name,password,email,address,phone);
    }

    @RequestMapping("/getUserSkills")
    public List<String> getUserSkills(@RequestParam("id") Integer id){return userService.getUserSkills(id);}

    @RequestMapping("/createData")
    public void createData(){userService.createData();}
  }
