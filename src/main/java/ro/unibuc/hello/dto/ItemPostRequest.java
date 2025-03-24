package ro.unibuc.hello.dto;

public class ItemPostRequest {

    private String name;
    private String description;
    
    public ItemPostRequest() {}

    public ItemPostRequest(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
