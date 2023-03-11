package ro.unibuc.hello.data;

import org.springframework.data.annotation.Id;

public class MovieEntity {
    @Id
    public String id;

    public String title;

    public String description;

    public Integer runtime; // duration of the movie will be shown in minutes

    public MovieEntity(String title, String description, Integer runtime){
        this.title = title;
        this.description = description;
        this.runtime = runtime;
    }
    @Override
    public String toString() {
        return String.format(
                "Movie[title='%s', description='%s', runtime='%d']",
                id, title, description, runtime);
    }
}
