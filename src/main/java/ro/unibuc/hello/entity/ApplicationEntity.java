package ro.unibuc.hello.entity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "applications")
public class ApplicationEntity {

    @Id
    public String id;

    public String project_id;
    public String user_id;

    /*
    *
    *  0 - pending
    *  1 - accepted
    *  2 - declined
    *
    * */
    public Integer status;

    public ApplicationEntity() {}

    public ApplicationEntity(String project_id, String user_id, Integer status) {
        this.project_id = project_id;
        this.user_id = user_id;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProject_id() {
        return project_id;
    }

    public void setProject_id(String project_id) {
        this.project_id = project_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ApplicationEntity{" +
                "project_id='" + project_id + '\'' +
                ", user_id='" + user_id + '\'' +
                ", status=" + status +
                '}';
    }
}