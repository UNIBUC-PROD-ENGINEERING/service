package ro.unibuc.hello.data;

import org.springframework.data.annotation.Id;

public class ActionEntity {

    @Id
    public String ActionCode;
    public String ActionDescription;

    public ActionEntity() {}

    public ActionEntity(String description){
        this.ActionDescription = description;

    }

    @Override
    public String toString() {
        return String.format(
            "Action[description='%s']",
            ActionCode, ActionDescription);
    }
}
