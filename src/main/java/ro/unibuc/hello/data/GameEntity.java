package ro.unibuc.hello.data;

import org.springframework.data.annotation.Id;
import lombok.*;

@Data
@AllArgsConstructor @NoArgsConstructor @Getter @Setter
public class GameEntity {
    @Id
    private String id;
    private String date;
    private int team1_id;
    private int team2_id;
    private String score;
    private int spectators;

    @Override
    public String toString() {
        return "GameEntity{" +
                "id='" + id + '\'' +
                ", date='" + date + '\'' +
                ", team1_id=" + team1_id +
                ", team2_id=" + team2_id +
                ", score='" + score + '\'' +
                ", spectators=" + spectators +
                '}';
    }
}
