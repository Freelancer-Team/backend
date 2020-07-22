package freelancer.serviceimpl;

import freelancer.entity.Job;
import freelancer.entity.User;
import freelancer.repository.SkillRepository;
import freelancer.repository.UserRepository;
import freelancer.repository.JobRepository;
import freelancer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.JWT;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User findUserByemail(String email){
        return userRepository.findUserByemail(email);
    }

    @Override
    public String getToken(User user) {
        String token="";
        token= JWT.create().withAudience(user.getEmail())
                .sign(Algorithm.HMAC256(user.getPassword()));
        return token;
    }

    @Override
    public List<User> getUsers() {
        List<User> list;
        list = userRepository.findAll();
        return list;
    }

    @Override
    public User updateSkills(List<String> skills,int userId){
        User user = userRepository.findById(userId).get();
        user.setSkills(skills);
        return userRepository.save(user);
    }

    @Override
    public User getUser(int id){
        if(userRepository.findById(id).isPresent())
        {
            User user = userRepository.findById(id).get();
            user.setPassword("已隐藏");
            return user;
        }
        else return null;
    }

    @Override
    public User login(String email, String password){
        User user = userRepository.login(email,password);
        return user;
    }

    @Override
    public User saveUser(User user)
    {
        return userRepository.save(user);
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

    @Override
    public List<String> getUserSkills(Integer id){
        List<String> a = null;
        if(userRepository.findById(id).isPresent())
        {
            a = userRepository.findById(id).get().getSkills();
        }
        return a;
    }

    @Autowired
    JobRepository jobRepository;
    @Override
    public void createRate(){
        List<Job> list;
        list = jobRepository.findAll();
        Job job;
        User user;
        double employeeRate;
        double employerRate;
        String obj1;
        DecimalFormat df = new DecimalFormat("#.#");
        for(int i=0;i<list.size();i++){
            job = list.get(i);
            if(job.getEmployeeRate()!=0)
            {
                user = userRepository.findById(job.getEmployeeId()).get();
                employeeRate = (user.getEmployeeRate()*user.getEmployeeNum()+job.getEmployeeRate())/(user.getEmployeeNum()+1);
                obj1 = df.format(employeeRate);
                employeeRate = Double.parseDouble(obj1);
                user.setEmployeeRate(employeeRate);
                user.setEmployeeNum(user.getEmployeeNum()+1);
                userRepository.save(user);
            }
            if(job.getEmployerRate()!=0)
            {
                user = userRepository.findById(job.getEmployerId()).get();
                employerRate = (user.getEmployerRate()*user.getEmployerNum()+job.getEmployerRate())/(user.getEmployerNum()+1);
                obj1 = df.format(employerRate);
                employerRate = Double.parseDouble(obj1);
                user.setEmployerRate(employerRate);
                user.setEmployerNum(user.getEmployerNum()+1);
                userRepository.save(user);
            }
        }
    }

    @Autowired
    SkillRepository skillRepository;

    @Override
    public String createRandom(int i){
        String name = "";
        for(int j=0;j<i;j++)
            name += (char)('a'+ Math.random()*25);
        return name;
    }

    @Override
    public void createData(){
        User user;
        for(int i=1;i<1218;i++)
        {
            user = new User();
            String name;
            int age;
            String gender;
            String password;
            String email;
            String address;
            String phone;
            int role;
            String time;
            String description;
            List<String> skills = new ArrayList<String>();
            int j;
            j = (int) (4+ Math.random()*3);
            name = (char)('A'+ Math.random()*25) + createRandom(j);
            age = 15 + (int) (Math.random()*50);
            j = (int) (Math.random()*2);
            if(j <1)
                gender = "F";
            else gender = "M";
            j = 3+(int) (Math.random()*5);
            password = "";
            for (int k=0;k<j;k++)
                password+=(int) (Math.random()*9);
            j = 6+(int) (Math.random()*5);
            email = createRandom(j)+'@'+"163.com";
            address = "Dongchuan Road 800";
            phone = "1";
            for (int k=0;k<10;k++)
                phone += (int) (Math.random()*9);
            role = 0;
            time = "2020/"+ (1+(int) (Math.random()* 6)) +'/'+(1+(int) (Math.random()* 30));
            description = "Don't have a description";
            j = 2 + (int) (Math.random()* 6);
            for (int k=0;k<j;k++)
            {
                int index = (int) (Math.random()* 1800);
                String skill = "";
                if(skillRepository.findId(index)!=null)
                    skill = skillRepository.findId(index).getName();
                if(skill!="")
                    skills.add(skill);
            }
            user.setId(i);
            user.setName(name);
            user.setAge(age);
            user.setGender(gender);
            user.setPassword(password);
            user.setEmail(email);
            user.setAddress(address);
            user.setPhone(phone);
            user.setRole(role);
            user.setTime(time);
            user.setDescription(description);
            user.setSkills(skills);
            user.setEmployeeRate(0);
            user.setEmployerRate(0);
            user.setEmployeeNum(0);
            user.setEmployerNum(0);
            userRepository.save(user);
        }
    }

}
