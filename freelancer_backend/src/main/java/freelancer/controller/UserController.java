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

    //管理员获得所有用户
    @RequestMapping("/getUsers")
    public List<User> getUsers() {
        return userService.getUsers();
    }

    //登录
    @RequestMapping("/login")
    public User login(@RequestParam("email") String email,@RequestParam("password") String password){
        return userService.login(email, password);
    }

    //注册
    @RequestMapping("/signup")
    public User signup(@RequestParam("name") String name,@RequestParam("password") String password,
                       @RequestParam("email") String email,@RequestParam("address") String address,
                       @RequestParam("phone") String phone){
        return userService.signup(name,password,email,address,phone);
    }

    //通过id获得用户技能
    @RequestMapping("/getUserSkills")
    public List<String> getUserSkills(@RequestParam("id") Integer id){return userService.getUserSkills(id);}

    //生成数据使用
    @RequestMapping("/createData")
    public void createData(){userService.createData();}

    //通过id获得user
    @RequestMapping("/getUser")
    public User getUser(@RequestParam("id") int id){return userService.getUser(id);}

    //保存user信息
    @RequestMapping("/saveUser")
    public User saveUser(@RequestBody User user){return userService.saveUser(user);}

    //生成用户评分
    @RequestMapping("/createRate")
    public void createRate(){ userService.createRate();}

    @RequestMapping("/updateSkills")
    public User updateSkills(@RequestBody List<String> skills,@RequestParam("userId") int userId){
        return userService.updateSkills(skills,userId);
    }
  }
