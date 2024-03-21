package ro.unibuc.triplea.infrastructure.favorites.steam.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import ro.unibuc.triplea.application.favorites.steam.dto.response.SteamGameFavoriteResponse;
import ro.unibuc.triplea.domain.auth.model.entity.meta.User;
import ro.unibuc.triplea.domain.favorites.steam.model.entity.SteamGameFavorite;
import ro.unibuc.triplea.domain.favorites.steam.repository.SteamGameFavoriteRepository;
import ro.unibuc.triplea.infrastructure.auth.repository.SpringDataUserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Primary
@RequiredArgsConstructor
public class SteamGameFavoriteRepositoryImpl implements SteamGameFavoriteRepository {
    private final SpringDataSteamGameFavoriteRepository springDataSteamGameFavoriteRepository;
    private final SpringDataUserRepository springDataUserRepository;


    @Override
    public Optional<List<SteamGameFavoriteResponse>> findAllByUserName(String username) {
        Optional<User> user = springDataUserRepository.findByUsername(username);
        if (user.isEmpty()) {
            return Optional.empty();
        }
        List<SteamGameFavorite> steamGameFavorites = springDataSteamGameFavoriteRepository.findAllByUserName(username);
        return Optional.of(steamGameFavorites.stream()
                .map(steamGameFavorite -> SteamGameFavoriteResponse.builder()
                        .gameSteamId(steamGameFavorite.getGameSteamId())
                        .gameName(steamGameFavorite.getGameName())
                        .userName(steamGameFavorite.getUserName())
                        .build())
                .collect(Collectors.toList()));
    }

    @Override
    public Optional<SteamGameFavoriteResponse> save(SteamGameFavorite steamGameFavorite) {
        Optional<SteamGameFavorite> duplicateFavorite = springDataSteamGameFavoriteRepository
                .findByUserNameAndGameSteamId(steamGameFavorite.getUserName(), steamGameFavorite.getGameSteamId());
        if (duplicateFavorite.isPresent()) {
            return Optional.empty();
        }
        SteamGameFavorite savedSteamGameFavorite = springDataSteamGameFavoriteRepository.save(steamGameFavorite);
        return Optional.of(SteamGameFavoriteResponse.builder()
                .gameSteamId(savedSteamGameFavorite.getGameSteamId())
                .gameName(savedSteamGameFavorite.getGameName())
                .userName(savedSteamGameFavorite.getUserName())
                .build());

    }
}
