package ro.unibuc.triplea.domain.reviews.steam.model.entity;

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
public class SteamGameReview extends GameReview {
    @Column(name = "gameSteamId", nullable = false)
    private int gameSteamId;
}
