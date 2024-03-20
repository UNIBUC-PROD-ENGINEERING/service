package ro.unibuc.hello.dtos;

import ro.unibuc.hello.entities.Action;

public class ActionDTO {
    private String code;
    private String description;

    public ActionDTO(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public Action toAction() {
        return new Action(this.code, this.description);
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

}
