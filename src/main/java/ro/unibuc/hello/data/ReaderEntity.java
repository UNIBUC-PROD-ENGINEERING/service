package ro.unibuc.hello.data;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class ReaderEntity {
    @Id
    private String readerId;

    private String name;
    private String nationality;
    private String email;
    private String phoneNumber;
    private LocalDate birthDate;
    private LocalDate registrationDate;

    @Override
    public String toString() {
        return String.format(
                "Reader[readerId='%s', name='%s', nationality='%s', email='%s', phoneNumber='%s', birthDate='%s', registrationDate='%s']",
                readerId, name, nationality, email, phoneNumber, birthDate, registrationDate);
    }
}
