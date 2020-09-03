package freelancer.controller;

import freelancer.entity.User;
import freelancer.security.ManagerLoginToken;
import freelancer.security.PassToken;
import freelancer.security.UserLoginToken;
import freelancer.service.UserService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;


    //管理员获得所有用户
    @ManagerLoginToken
    @GetMapping("/getUsers")
    public List<User> getUsers() {
        return userService.getUsers();
    }

    //登录
    @PassToken
    @PostMapping("/login")
    public Object login(@RequestParam("email") String email,@RequestParam("password") String password){
        System.out.println(email);
        System.out.println(password);
        JSONObject jsonObject=new JSONObject();
        User userForBase=userService.findUserByemail(email);
        if(userForBase==null){
            jsonObject.put("message","登录失败,用户不存在");
            return jsonObject;
        }else {
            if (!userForBase.getPassword().equals(password)){
                jsonObject.put("message","登录失败,密码错误");
            }
            else {
                String token = userService.getToken(userForBase);
                jsonObject.put("token", token);
                jsonObject.put("user", userForBase);
            }
            return jsonObject;
        }
    }

    //注册
    @PassToken
    @RequestMapping("/signup")
    public User signup(@RequestParam("name") String name,@RequestParam("password") String password,
                       @RequestParam("email") String email,@RequestParam("address") String address,
                       @RequestParam("phone") String phone){
        return userService.signup(name,password,email,address,phone);
    }

    //通过id获得用户技能
    @UserLoginToken
    @RequestMapping("/getUserSkills")
    public List<String> getUserSkills(@RequestParam("id") Integer id){return userService.getUserSkills(id);}

    //生成数据使用
    @RequestMapping("/createData")
    public void createData(){userService.createData();}

    //通过id获得user
    @UserLoginToken
    @RequestMapping("/getUser")
    public User getUser(@RequestParam("id") int id){return userService.getUser(id);}

    //保存user信息
    @UserLoginToken
    @RequestMapping("/saveUser")
    public User saveUser(@RequestBody User user){return userService.saveUser(user);}

    //生成用户评分
    @RequestMapping("/createRate")
    public void createRate(){ userService.createRate();}

    @UserLoginToken
    @RequestMapping("/updateSkills")
    public User updateSkills(@RequestBody List<String> skills,@RequestParam("userId") int userId){
        return userService.updateSkills(skills,userId);
    }

    @ManagerLoginToken
    @RequestMapping("setUserRole")
    public void setUserRole(@RequestParam("userId") int userId,@RequestParam("role") int role){
        userService.setUserRole(userId,role);
    }

    @RequestMapping("/updateNum")
    public void updateNum(){userService.updateNum();}

    @RequestMapping("/addShow")
    public void addShow(){userService.addShow();}

    @ManagerLoginToken
    @PostMapping("/setShow")
    public void setShow(@RequestParam("userId") int userId){userService.setShow(userId);}
  }
