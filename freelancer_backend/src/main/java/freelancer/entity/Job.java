package freelancer.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "jobs")
public class Job {

    @Id
    private String id;
    private String[] skills;
    private String price;
    private String description;
    private String remaining_time;
    private String title;
    private Integer peopleNumber;
    private String employeeName;
    private int employeeId;
    private String employerName;
    private int employerId;
    private double employeeRate;
    private double employerRate;
    private String startTime;
    private String finishTime;
    private int avgPrice;
    private int lowestPrice;

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public int getEmployerId() {
        return employerId;
    }

    public String getEmployerName() {
        return employerName;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public void setEmployerId(int employerId) {
        this.employerId = employerId;
    }

    public void setEmployerName(String employerName) {
        this.employerName = employerName;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPrice() {
        return price;
    }

    public void setRemaining_time(String remaining_time) {
        this.remaining_time = remaining_time;
    }

    public String getRemaining_time() {
        return remaining_time;
    }

    public void setSkills(String[] skills) {
        this.skills = skills;
    }

    public String[] getSkills() {
        return skills;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setId(ObjectId id) {
        this.id = id.toString();
    }

    public String getId() {
        return id;
    }
}
