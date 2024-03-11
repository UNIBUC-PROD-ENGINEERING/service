package ro.unibuc.hello.data;

import org.springframework.data.annotation.Id;

import java.util.List;

public class TeamEntity {
    @Id
    public String id;
    public String name;
    public List<Integer> players;
    public int yearFounded;
    public String coach;

    public TeamEntity(String name, List<Integer> players, int yearFounded, String coach) {
        this.name = name;
        this.players = players;
        this.yearFounded = yearFounded;
        this.coach = coach;
    }
    public String getTeamInfo() {
        return "Team " + name + " was founded in " + yearFounded + ". The coach is " + coach + ".";
    }
}
