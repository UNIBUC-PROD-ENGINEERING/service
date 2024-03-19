package ro.unibuc.triplea.domain.games.steam.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@SuperBuilder
public class SteamGame extends Game {

    @Column(name = "gameSteamId", nullable = false)
    private int gameSteamId;
}
