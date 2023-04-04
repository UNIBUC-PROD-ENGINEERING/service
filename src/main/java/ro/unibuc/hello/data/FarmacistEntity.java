package ro.unibuc.hello.data;

import org.springframework.data.annotation.Id;
import ro.unibuc.hello.dto.Farmacist;

import java.util.ArrayList;

public class FarmacistEntity {

    @Id
    public long id;
    public String name;
    public ArrayList<Farmacist> farmacisti = new ArrayList<>();
    private static long counter = 1;
    public FarmacistEntity() {
    }

    public FarmacistEntity(String name, ArrayList<Farmacist> med) {
        this.id = counter;
        counter++;
        this.name = name;
        this.farmacisti = med;
    }

    public Farmacist getFarmacist(long id){
        for (Farmacist m: farmacisti) {
            if(m.getId()==id)
                return m;

        }
        return null;
    }

    public ArrayList<Farmacist> delFarmacist(long id){
        System.out.println("id= "+id);
        for (int i=0; i<farmacisti.size(); i++) {
            if(farmacisti.get(i).getId()==id)
            {
                farmacisti.remove(i);
                break;
            }

        }
        return farmacisti;
    }

    public void delFarmacist(){
        farmacisti=new ArrayList<Farmacist>();
    }

    public void addFarmacist(Farmacist m){
        this.farmacisti.add(m);
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

    public ArrayList<Farmacist> getFarmacisti() {
        return farmacisti;
    }

    public void setFarmacisti(ArrayList<Farmacist> farmacisti) {
        this.farmacisti = farmacisti;
    }


    @Override
    public String toString() {
        return "FarmacistEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", farmacisti=" + farmacisti +
                '}';
    }
}
