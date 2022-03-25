package ro.unibuc.hello.data;

import org.springframework.data.annotation.Id;

import java.util.ArrayList;

public class RecipeEntity {

    @Id
    public String id;
    public String name;
    public ArrayList<String> ingredientsNames;

    public RecipeEntity(String name, ArrayList<String> ingredientsNames) {
        this.name = name;
        this.ingredientsNames = ingredientsNames;
    }

}
