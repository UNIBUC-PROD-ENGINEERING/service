package ro.unibuc.hello.dto;

import java.util.Arrays;

public class Medicamente {

    private Medicament[] medicamente = new Medicament[0];

    public Medicamente(Medicament[] medicamente) {
        this.medicamente = medicamente;
    }

    public void addMedicament(Medicament m){
        Medicament[] mex = new Medicament[this.medicamente.length+1];
        System.arraycopy(this.medicamente, 0, mex, 0, mex.length - 1);
        mex[mex.length-1] = m;
        this.medicamente = mex;
    }

    public Medicament[] getMedicamente() {
        return medicamente;
    }

    public void setMedicamente(Medicament[] medicamente) {
        this.medicamente = medicamente;
    }

    @Override
    public String toString() {
        return "Medicamente{" +
                "medicamente=" + Arrays.toString(medicamente) +
                '}';
    }
}
