package freelancer.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;


@Document(collection = "jobs")
public class Job {

    @Id
    private String id;
    private List<String> skills;
    private String price;
    private int low;
    private int high;
    private int type;
    private String description;
    private String remaining_time;
    private String title;
    private int state;
    private String publishTime;
    private String deadline;
    private int click;
    private int candidateNum;
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

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setLow(int low) {
        this.low = low;
    }

    public void setHigh(int high) {
        this.high = high;
    }

    public int getHigh() {
        return high;
    }

    public int getLow() {
        return low;
    }

    public void setCandidateNum(int candidateNum) {
        this.candidateNum = candidateNum;
    }

    public int getCandidateNum() {
        return candidateNum;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setClick(int click) {
        this.click = click;
    }

    public int getClick() {
        return click;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getState() {
        return state;
    }

    public int getAvgPrice() {
        return avgPrice;
    }

    public int getLowestPrice() {
        return lowestPrice;
    }

    public void setAvgPrice(int avgPrice) {
        this.avgPrice = avgPrice;
    }

    public void setLowestPrice(int lowestPrice) {
        this.lowestPrice = lowestPrice;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setEmployeeRate(double employeeRate) {
        this.employeeRate = employeeRate;
    }

    public double getEmployeeRate() {
        return employeeRate;
    }

    public void setEmployerRate(double employerRate) {
        this.employerRate = employerRate;
    }

    public double getEmployerRate() {
        return employerRate;
    }

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

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public List<String> getSkills() {
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
