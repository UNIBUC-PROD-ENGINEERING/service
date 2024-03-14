package ro.unibuc.hello.dto.tmdb;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieApiDto {
    @JsonAlias("id")
    private Integer tmdbId;

    @JsonAlias("original_title")
    private String title;

    private String overview;

    private List<GenreDto> genres;

    private Integer popularity;

    @JsonAlias("release_date")
    private String releaseDate;
}
