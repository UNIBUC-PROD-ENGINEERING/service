package ro.unibuc.hello.data;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
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