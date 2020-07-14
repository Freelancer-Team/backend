package freelancer.serviceimpl;

import freelancer.entity.User;
import freelancer.repository.UserRepository;
import freelancer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getUsers() {
        List<User> list;
        list = userRepository.findAll();
        return list;
    }

    @Override
    public User login(String name, String password){
        User user = userRepository.login(name,password);
        return user;
    }

    @Override
    public User signup(String name,String password,String email,String address,String phone){
        User user = new User();
        List<User> list;
        list = userRepository.findAll();
        int maxIndex = list.size()-1;
        int max = 1 + list.get(maxIndex).getId();
        user.setId(max);
        user.setRole(0);
        user.setName(name);
        user.setPassword(password);
        user.setEmail(email);
        user.setAddress(address);
        user.setPhone(phone);
        return userRepository.save(user);
    }
}
