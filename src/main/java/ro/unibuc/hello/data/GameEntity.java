package ro.unibuc.hello.data;

import org.springframework.data.annotation.Id;

public class GameEntity {
    @Id
    private String id;
    private String date;
    private int team1_id;
    private int team2_id;
    private String score;
    private int spectators;

    public GameEntity(String date, int team1_id, int team2_id, String score, int spectators) {
        this.date = date;
        this.team1_id = team1_id;
        this.team2_id = team2_id;
        this.score = score;
        this.spectators = spectators;
    }

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

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getTeam1_id() {
        return team1_id;
    }

    public void setTeam1_id(int team1_id) {
        this.team1_id = team1_id;
    }

    public int getTeam2_id() {
        return team2_id;
    }

    public void setTeam2_id(int team2_id) {
        this.team2_id = team2_id;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public int getSpectators() {
        return spectators;
    }

    public void setSpectators(int spectators) {
        this.spectators = spectators;
    }
}
