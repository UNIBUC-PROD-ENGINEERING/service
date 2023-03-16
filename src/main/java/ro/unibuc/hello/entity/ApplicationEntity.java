package ro.unibuc.hello.entity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "applicationEntity")
public class ApplicationEntity {

    @Id
    public String id;
    @Indexed
    public String projectId;
    public String userId;

    /*
    *
    *  0 - pending
    *  1 - accepted
    *  2 - declined
    *
    * */
    public Integer status;

    public ApplicationEntity() {}

    public ApplicationEntity(String projectId, String userId, Integer status) {
        this.projectId = projectId;
        this.userId = userId;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
                "projectId='" + projectId + '\'' +
                ", userId='" + userId + '\'' +
                ", status=" + status +
                '}';
    }
}