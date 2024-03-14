package ro.unibuc.hello.data;

import org.springframework.data.annotation.Id;

public class PlayerEntity {
    @Id
    public String id;
    public String name;
    public String team;
    public double points_per_game;
    public double rebounds_per_game;
    public double assists_per_game;

    public PlayerEntity() {
    }

    public PlayerEntity(String id, String name, String team, double points_per_game, double rebounds_per_game,
            double assists_per_game) {
        this.id=id;
        this.name = name;
        this.team = team;
        this.points_per_game = points_per_game;
        this.rebounds_per_game = rebounds_per_game;
        this.assists_per_game = assists_per_game;
    }

    public String getTeam() {
        return team;
    }

    @Override
    public String toString() {
        return String.format(
                "Player: %s\nTeam: %s\nPoints per game: %.2f\nRebounds per game: %.2f\nAssists per game: %.2f",
                name, team, points_per_game, rebounds_per_game, assists_per_game);
    }

}
