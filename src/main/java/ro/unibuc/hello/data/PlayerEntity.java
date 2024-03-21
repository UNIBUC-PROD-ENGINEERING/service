package ro.unibuc.hello.data;

import org.springframework.data.annotation.Id;
import lombok.*;

@Data
@AllArgsConstructor @NoArgsConstructor @Getter @Setter
public class PlayerEntity {
    @Id
    public String id;
    public String name;
    public String team;
    public double points_per_game;
    public double rebounds_per_game;
    public double assists_per_game;

    @Override
    public String toString() {
        return String.format(
                "Player: %s\nTeam: %s\nPoints per game: %.2f\nRebounds per game: %.2f\nAssists per game: %.2f",
                name, team, points_per_game, rebounds_per_game, assists_per_game);
    }
}
