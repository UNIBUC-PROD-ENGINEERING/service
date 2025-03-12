package ro.unibuc.hello.dto;

public class Game{    
    private String id;
    private String title;
    private int tier;

    public Game() {}

    public Game(String id, String title, int tier) {
        this.id = id;
        this.title = title;
        this.tier = tier;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTier(){
        return tier;
    }

    public void setTier(int tier){
        this.tier = tier;
    }
}