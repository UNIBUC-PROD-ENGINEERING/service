package ro.unibuc.hello.data;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class AuthorEntity {
    @Id
    private String authorId;

    private String name;
    private String nationality;
    private LocalDate birthDate;
    private LocalDate deathDate;

    @Override
    public String toString() {
        return String.format(
                "Author[authorId='%s', name='%s', nationality='%s']",
                authorId, name, nationality);
    }

}