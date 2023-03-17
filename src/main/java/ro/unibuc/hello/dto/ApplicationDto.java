package ro.unibuc.hello.dto;

import org.springframework.data.annotation.Id;

public class ApplicationDto {

    @Id
    public String id;

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

    public ApplicationDto(String projectId, String userId, Integer status) {
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

    public void setUserId(String user_id) {
        this.userId = userId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}