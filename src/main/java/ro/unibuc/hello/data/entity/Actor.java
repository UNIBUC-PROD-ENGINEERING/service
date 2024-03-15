package ro.unibuc.hello.data.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.annotation.Id;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Actor {
    @Id private String id;
    @NotNull private String name;
    @NotNull private Long tmdbId;
}
