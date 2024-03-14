package ro.unibuc.hello.data.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Movie {
    @Id private Long id;
    @NotNull private String title;
    @NotNull private String description;
    private Integer popularity;
    private List<String> genres;
    private Long year;
}
