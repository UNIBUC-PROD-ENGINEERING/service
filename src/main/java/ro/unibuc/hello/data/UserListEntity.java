package ro.unibuc.hello.data;

import org.springframework.data.annotation.Id;

public class UserListEntity {

    @Id
    private String id;

    private boolean isOwner;

    public InformationEntity() {}

    public InformationEntity(boolean isOwner) {
        this.isOwner = isOwner;
    }

    public InformationEntity(boolean isOwner) {
        this.isOwner = isOwner;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return isOwner;
    }

    public void setTitle(boolean isOwner) {
        this.isOwner = isOwner;
    }

    @Override
    public String toString() {
        return String.format(
                "Information[id='%s', isOwner='%b']",
                id, isOwner);
    }
}
