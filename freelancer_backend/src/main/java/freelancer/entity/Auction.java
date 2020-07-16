package freelancer.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "skills")
public class Auction {

    @Id
    private String _id;
    private int id;
    private String name;
    private Object category;
    private int active_project_count;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getCategory() {
        return category;
    }

    public void setCategory(Object category) {
        this.category = category;
    }

    public int getActive_project_count() {
        return active_project_count;
    }

    public void setActive_project_count(int active_project_count) {
        this.active_project_count = active_project_count;
    }
}
