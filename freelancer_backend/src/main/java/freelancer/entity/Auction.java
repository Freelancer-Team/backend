package freelancer.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "auction")
public class Auction {

    @Id
    private String id;
    private String projectId;
    private String projectName;
    private int userId;
    private String userName;
    private String description;
    private List<String> skills;
    private String price;

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDescription() {
        return description;
    }

    public String getId() {
        return id;
    }

    public String getPrice() {
        return price;
    }

    public int getUserId() {
        return userId;
    }

    public String getProjectId() {
        return projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getUserName() {
        return userName;
    }

    public List<String> getSkills() {
        return skills;
    }
}
