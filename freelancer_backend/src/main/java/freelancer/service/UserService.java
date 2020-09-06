package freelancer.service;

import freelancer.entity.User;

import java.util.List;


public interface UserService {
    List<User> getUsers();
    User login(String email, String password);
    User signup(String name,String password,String email,String address,String phone);
    List<String> getUserSkills(Integer id);
    User getUser(int id);
    User saveUser(User user);
    User updateSkills(List<String> skills,int userId);
    String getToken(User user);
    User findUserByemail(String email);
    void setUserRole(int userId,int role);
    void setShow(int userId);
    void updateUserInfo(int id, String name, String gender, int age, String address, String phone, String description,String image);
    List<String> getOnesIcon(int userId);
}
