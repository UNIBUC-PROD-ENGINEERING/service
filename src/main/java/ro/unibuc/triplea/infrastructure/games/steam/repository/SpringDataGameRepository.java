package ro.unibuc.triplea.infrastructure.games.steam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.unibuc.triplea.domain.games.steam.model.entity.Game;

public interface SpringDataGameRepository extends JpaRepository<Game, Integer> {
}
