package ro.unibuc.hello.dto;

public class Product{

    private String id;
    private String productName;
    private String description;
    private String categories;

    public Product() {
    }

    public Product(long id, String description, String categories) {
        this.id = id;
        this.description = description;
        this.categories =  categories;
    }

    public void setId(long id) {
        this.id = id;
    }


    public long getId() {
        return id;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public long getDescription() {
        return Description;
    }
    public void setCategories(String categories) {
        this.categories = categories;
    }

    public long getCategories() {
        return categories;
    }

    

}