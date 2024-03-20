package ro.unibuc.triplea.domain.favorites.steam.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@SuperBuilder
@NoArgsConstructor
public class SteamGameFavorite extends GameFavorite {
    @Column(name = "gameSteamId", nullable = false)
    private int gameSteamId;
}
