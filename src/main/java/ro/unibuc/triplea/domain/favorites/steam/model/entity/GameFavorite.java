package ro.unibuc.triplea.domain.favorites.steam.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ro.unibuc.triplea.domain.games.steam.model.entity.BaseEntity;

@Entity
@Table(name = "Favorites")
@Data
@EqualsAndHashCode(callSuper = false)
@SuperBuilder
@NoArgsConstructor
public class GameFavorite extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "userName", nullable = false)
    private String userName;

    @Column(name = "gameName", nullable = false)
    private String gameName;
}
