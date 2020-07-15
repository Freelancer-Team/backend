package freelancer.service;

import freelancer.entity.User;

import java.util.List;


public interface UserService {
    List<User> getUsers();
    User login(String email, String password);
    User signup(String name,String password,String email,String address,String phone);
    List<String> getUserSkills(Integer id);
    void createData();
    String createRandom(int i);
}
