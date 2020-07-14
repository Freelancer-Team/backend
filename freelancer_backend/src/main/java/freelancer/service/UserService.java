package freelancer.service;

import freelancer.entity.User;

import java.util.List;


public interface UserService {
    List<User> getUsers();
    User login(String name, String password);
    User signup(String name,String password,String email,String address,String phone);
}
