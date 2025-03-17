package ro.unibuc.hello.data;

import org.springframework.data.annotation.Id;

public class GameEntity {

    @Id
    private String id;

    private String title;
    private int tier;

    public GameEntity() {}

    public GameEntity(String title, int tier) {
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

    @Override
    public String toString() {
        return String.format(
                "Game[title='%s', tier='%d']",
                title, tier);
    }
}
