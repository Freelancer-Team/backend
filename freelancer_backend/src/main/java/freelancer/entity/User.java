package freelancer.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Document(collection = "user")
public class User {

    @Id
    private Integer id;
    private String name;
    private int age;
    private String gender;
    private String password;
    private String email;
    private String address;
    private String phone;
    private int role;
    private int workNumber;
    private int publishNumber;
    private String time;
    private String description;
    private List<String> skills;
    private double employeeRate;
    private int employeeNum;
    private double employerRate;
    private int employerNum;
    private int isShow;

    public int getIsShow() {
        return isShow;
    }

    public void setIsShow(int isShow) {
        this.isShow = isShow;
    }

    public int getPublishNumber() {
        return publishNumber;
    }

    public void setPublishNumber(int publishNumber) {
        this.publishNumber = publishNumber;
    }

    public int getWorkNumber() {
        return workNumber;
    }

    public void setWorkNumber(int workNumber) {
        this.workNumber = workNumber;
    }

    public void setEmployeeNum(int employeeNum) {
        this.employeeNum = employeeNum;
    }

    public void setEmployerNum(int employerNum) {
        this.employerNum = employerNum;
    }

    public int getEmployeeNum() {
        return employeeNum;
    }

    public int getEmployerNum() {
        return employerNum;
    }

    public void setEmployerRate(double employerRate) {
        this.employerRate = employerRate;
    }

    public void setEmployeeRate(double employeeRate) {
        this.employeeRate = employeeRate;
    }

    public double getEmployerRate() {
        return employerRate;
    }

    public double getEmployeeRate() {
        return employeeRate;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public int getRole() {
        return role;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public List<String> getSkills() {
        return skills;
    }
}
