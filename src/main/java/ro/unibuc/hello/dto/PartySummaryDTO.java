package ro.unibuc.hello.dto;

public class PartySummaryDTO {
    private String id;
    private String name;
    private String date;

    public PartySummaryDTO(String id, String name, String date) {
        this.id = id;
        this.name = name;
        this.date = date;
    }

    // Getters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getDate() { return date; }
}
