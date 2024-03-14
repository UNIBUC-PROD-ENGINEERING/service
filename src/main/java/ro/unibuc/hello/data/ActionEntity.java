package ro.unibuc.hello.data;

import org.springframework.data.annotation.Id;

public class ActionEntity {

    @Id
    public String actionCode;
    public String actionDescription;

    public ActionEntity() {}

    public ActionEntity(String code, String description){
        this.actionCode = code;
        this.actionDescription = description;
    }

    @Override
    public String toString() {
        return String.format(
            "Action[cod=%s, description='%s']",
            actionCode, actionDescription);
    }
}
