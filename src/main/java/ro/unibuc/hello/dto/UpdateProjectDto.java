package ro.unibuc.hello.dto;

public class UpdateProjectDto {
    private String name;
    private String description;


    public UpdateProjectDto(String name, String description) {
        // Only have name and description in JSON because we can't change the project owner
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
