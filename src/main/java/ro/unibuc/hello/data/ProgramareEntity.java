package ro.unibuc.hello.data;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;

public class ProgramareEntity {
    @Id
    public String id;
    public IntervalOrarEntity intervalOrar;
    public DoctorEntity doctor;
    public LocalDate data;

    public ProgramareEntity() {
    }

    public ProgramareEntity(IntervalOrarEntity intervalOrar, DoctorEntity doctor, LocalDate data) {
        this.intervalOrar = intervalOrar;
        this.doctor = doctor;
        this.data = data;
    }

    @Override
    public String toString() {
        return String.format(
                "Programare[id='%s', intervalOrar='%s', doctor='%s', data='%s']",
                id, intervalOrar.toString(), doctor.toString(), data.toString());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public IntervalOrarEntity getIntervalOrar() {
        return intervalOrar;
    }

    public void setIntervalOrar(IntervalOrarEntity intervalOrar) {
        this.intervalOrar = intervalOrar;
    }

    public DoctorEntity getDoctor() {
        return doctor;
    }

    public void setDoctor(DoctorEntity doctor) {
        this.doctor = doctor;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }
}
