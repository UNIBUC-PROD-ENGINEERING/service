package ro.unibuc.hello.data;
import java.time.LocalDate;

import org.springframework.data.annotation.Id;

public class ProgramareEntity {


    @Id
    public String id;
    public String idIntervalOrar;
    public String idDoctor;
    public LocalDate data;

    public ProgramareEntity() {}

    public ProgramareEntity(String idIntervalOrar, String idDoctor, LocalDate data) {
        this.idIntervalOrar = idIntervalOrar;
        this.idDoctor = idDoctor;
        this.data = data;
    }

    @Override
    public String toString() {
        return String.format(
                "Programare[id='%s', idIntervalOrar='%s', idDoctor='%s', data='%s']",
                id, idIntervalOrar, idDoctor, data.toString());
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
