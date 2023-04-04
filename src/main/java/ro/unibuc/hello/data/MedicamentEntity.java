package ro.unibuc.hello.data;

import org.springframework.data.annotation.Id;
import ro.unibuc.hello.dto.Medicament;

import java.util.ArrayList;

public class MedicamentEntity {

    @Id
    public long id;
    public String name;
    public ArrayList<Medicament> medicamente = new ArrayList<>();
    private static long counter = 1;

    public MedicamentEntity() {
    }

    public MedicamentEntity(String name, ArrayList<Medicament> med) {
        this.id = counter;
        counter++;
        this.name = name;
        this.medicamente = med;
    }

    public Medicament getMedicament(long id){
        for (Medicament m: medicamente) {
            if(m.getId()==id)
                return m;

        }
        return null;
    }

    public ArrayList<Medicament> delMedicament(long id){
        System.out.println("id= "+id);
        for (int i=0; i<medicamente.size(); i++) {
            if(medicamente.get(i).getId()==id)
            {
                medicamente.remove(i);
                break;
            }

        }
        return medicamente;
    }

    public void delMedicamente(){
        medicamente=new ArrayList<Medicament>();
    }

    public void addMedicament(Medicament m){
        this.medicamente.add(m);
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

    public ArrayList<Medicament> getMedicamente() {
        return medicamente;
    }

    public void setMedicamente(ArrayList<Medicament> medicamente) {
        this.medicamente = medicamente;
    }

    @Override
    public String toString() {
        return "MedicamentEntity{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", medicamente=" + medicamente +
                '}';
    }
}
