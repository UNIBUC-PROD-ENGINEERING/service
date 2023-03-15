package ro.unibuc.hello.dto;

import org.springframework.data.annotation.Id;

import java.util.Arrays;

public class Medicament {

    private static long counter = 1;
    private @Id long id;
    private String name;
    private String[] ingredients;

    public Medicament(){

    }

    public Medicament(String name, String[] ing){
        this.id=counter;
        this.name =name;
        this.ingredients = ing;
        counter++;
    }

    public Medicament[] Extend(Medicament[] m){
        Medicament[] newm = new Medicament[m.length+1];
        for(int i=0; i<m.length; i++){
            newm[i]=m[i];
        }
        return newm;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getIngredients() {
        return ingredients;
    }

    public void setIngredients(String[] ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public String toString() {
        return "Medicament{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", ingredients=" + Arrays.toString(ingredients) +
                '}';
    }
}
