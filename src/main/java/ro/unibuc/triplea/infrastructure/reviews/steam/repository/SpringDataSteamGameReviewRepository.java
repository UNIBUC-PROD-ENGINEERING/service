package ro.unibuc.triplea.infrastructure.reviews.steam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.unibuc.triplea.domain.reviews.steam.model.entity.SteamGameReview;

import java.util.List;

public interface SpringDataSteamGameReviewRepository extends JpaRepository<SteamGameReview, Integer> {
    List<SteamGameReview> findAllByUserName(String userName);

    List<SteamGameReview> findAllByGameSteamId(int gameSteamId);

    SteamGameReview findById(int id);
}
