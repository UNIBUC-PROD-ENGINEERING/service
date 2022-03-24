package ro.unibuc.hello.dto;

import java.util.ArrayList;

public class AddRecipeDto {

    public String name;
    public ArrayList<String> ingredientsNames;
    public AddRecipeDto(String name, ArrayList<String> ingredientsNames) {
        this.name = name;
        this.ingredientsNames = ingredientsNames;
    }
}
