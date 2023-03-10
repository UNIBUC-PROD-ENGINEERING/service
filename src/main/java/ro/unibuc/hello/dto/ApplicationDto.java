package ro.unibuc.hello.dto;

import org.springframework.data.annotation.Id;

public class ApplicationDto {

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

    public ApplicationDto(String project_id, String user_id, Integer status) {
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
}