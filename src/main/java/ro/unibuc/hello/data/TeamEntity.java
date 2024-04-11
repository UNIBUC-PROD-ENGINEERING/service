package ro.unibuc.hello.data;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.springframework.data.annotation.Id;
import java.util.List;
import lombok.*;
@JsonIgnoreProperties(ignoreUnknown = true)

@Data
@AllArgsConstructor @NoArgsConstructor @Getter @Setter
public class TeamEntity {
    @Id
    public String id;
    public String name;
    public List<Integer> players;
    public int yearFounded;
    public String coach;

    public String getTeamInfo() {
        return "Team " + name + " was founded in " + yearFounded + ". The coach is " + coach + ".";
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Team ID: ").append(id).append("\n");
        sb.append("Name: ").append(name).append("\n");
        sb.append("Year Founded: ").append(yearFounded).append("\n");
        sb.append("Coach: ").append(coach).append("\n");
        sb.append("Players: ");
        for (Integer playerId : players) {
            sb.append(playerId).append(", ");
        }
        sb.delete(sb.length() - 2, sb.length()); // Eliminăm ultima virgulă și spațiul adăugate în plus
        return sb.toString();
    }

}
