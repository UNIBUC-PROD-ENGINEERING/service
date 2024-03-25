package ro.unibuc.triplea.infrastructure.reviews.steam.repository;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import ro.unibuc.triplea.application.reviews.steam.dto.response.SteamGameReviewResponse;
import ro.unibuc.triplea.domain.auth.model.entity.meta.User;
import ro.unibuc.triplea.domain.games.steam.gateway.SteamGameGateway;
import ro.unibuc.triplea.domain.games.steam.model.entity.SteamGame;
import ro.unibuc.triplea.domain.reviews.steam.model.entity.SteamGameReview;
import ro.unibuc.triplea.infrastructure.auth.repository.SpringDataUserRepository;

public class SteamGameReviewRepositoryImplTest {

    private final SpringDataSteamGameReviewRepository springDataSteamGameReviewRepository = mock(SpringDataSteamGameReviewRepository.class);

    private final SpringDataUserRepository springDataUserRepository = mock(SpringDataUserRepository.class);

    private final SteamGameGateway steamGameGateway = mock(SteamGameGateway.class);

    private SteamGameReviewRepositoryImpl steamGameReviewRepositoryImpl = new SteamGameReviewRepositoryImpl(springDataSteamGameReviewRepository, springDataUserRepository, steamGameGateway);

    @Test
    public void testFindAllByGameSteamId() {
        when(steamGameGateway.getSteamGameBySteamId(anyInt()))
                .thenReturn(Optional.of(SteamGame.builder().gameSteamId(1).gameName("game1").build()));
        List<SteamGameReview> reviews = new ArrayList<>();
        reviews.add(SteamGameReview.builder()
                .gameSteamId(1)
                .gameName("game1")
                .userName("user1")
                .reviewContent("review1")
                .build());
        reviews.add(SteamGameReview.builder()
                .gameSteamId(1)
                .gameName("game1")
                .userName("user2")
                .reviewContent("review2")
                .build());

        List<SteamGameReviewResponse> expectedReviewResponses = new ArrayList<>();
        expectedReviewResponses.add(SteamGameReviewResponse.builder()
                .gameSteamId(1)
                .gameName("game1")
                .userName("user1")
                .reviewContent("review1")
                .build());
        expectedReviewResponses.add(SteamGameReviewResponse.builder()
                .gameSteamId(1)
                .gameName("game1")
                .userName("user2")
                .reviewContent("review2")
                .build());

        when(springDataSteamGameReviewRepository.findAllByGameSteamId(1)).thenReturn(reviews);

        var result = steamGameReviewRepositoryImpl.findAllByGameSteamId(1);

        assertEquals(Optional.of(expectedReviewResponses), result);
    }

    @Test
    public void testFindAllByGameSteamId_NonexistentGame() {
        when(steamGameGateway.getSteamGameBySteamId(anyInt())).thenReturn(Optional.empty());

        var result = steamGameReviewRepositoryImpl.findAllByGameSteamId(1);

        assertEquals(Optional.empty(), result);
    }

    @Test
    public void testFindAllByGameName() {
        when(steamGameGateway.getSteamGameByName(anyString()))
                .thenReturn(Optional.of(SteamGame.builder().gameSteamId(1).gameName("game1").build()));
        List<SteamGameReview> reviews = new ArrayList<>();
        reviews.add(SteamGameReview.builder()
                .gameSteamId(1)
                .gameName("game1")
                .userName("user1")
                .reviewContent("review1")
                .build());
        reviews.add(SteamGameReview.builder()
                .gameSteamId(1)
                .gameName("game1")
                .userName("user2")
                .reviewContent("review2")
                .build());

        List<SteamGameReviewResponse> expectedReviewResponses = new ArrayList<>();
        expectedReviewResponses.add(SteamGameReviewResponse.builder()
                .gameSteamId(1)
                .gameName("game1")
                .userName("user1")
                .reviewContent("review1")
                .build());
        expectedReviewResponses.add(SteamGameReviewResponse.builder()
                .gameSteamId(1)
                .gameName("game1")
                .userName("user2")
                .reviewContent("review2")
                .build());

        when(springDataSteamGameReviewRepository.findAllByGameSteamId(1)).thenReturn(reviews);

        var result = steamGameReviewRepositoryImpl.findAllByGameName("game1");

        assertEquals(Optional.of(expectedReviewResponses), result);
    }

    @Test
    public void testFindAllByGameName_NonexistentGame() {
        when(steamGameGateway.getSteamGameByName(anyString())).thenReturn(Optional.empty());

        var result = steamGameReviewRepositoryImpl.findAllByGameName("nonexistentGame");

        assertEquals(Optional.empty(), result);
    }

    @Test
    public void testFindAllByUserName() {
        when(springDataUserRepository.findByUsername(anyString()))
                .thenReturn(Optional.of(User.builder().username("user1").build()));
        List<SteamGameReview> reviews = new ArrayList<>();
        reviews.add(SteamGameReview.builder()
                .gameSteamId(1)
                .gameName("game1")
                .userName("user1")
                .reviewContent("review1")
                .build());
        reviews.add(SteamGameReview.builder()
                .gameSteamId(2)
                .gameName("game2")
                .userName("user1")
                .reviewContent("review2")
                .build());

        List<SteamGameReviewResponse> expectedReviewResponses = new ArrayList<>();
        expectedReviewResponses.add(SteamGameReviewResponse.builder()
                .gameSteamId(1)
                .gameName("game1")
                .userName("user1")
                .reviewContent("review1")
                .build());
        expectedReviewResponses.add(SteamGameReviewResponse.builder()
                .gameSteamId(2)
                .gameName("game2")
                .userName("user1")
                .reviewContent("review2")
                .build());

        when(springDataSteamGameReviewRepository.findAllByUserName("user1")).thenReturn(reviews);

        var result = steamGameReviewRepositoryImpl.findAllByUserName("user1");

        assertEquals(Optional.of(expectedReviewResponses), result);
    }

    @Test
    public void testFindAllByUserName_NonexistentUser() {
        when(springDataUserRepository.findByUsername(anyString())).thenReturn(Optional.empty());

        var result = steamGameReviewRepositoryImpl.findAllByUserName("nonexistentUser");

        assertEquals(Optional.empty(), result);
    }

    @Test
    public void testSave() {
        when(steamGameGateway.getSteamGameBySteamId(anyInt()))
                .thenReturn(Optional.of(SteamGame.builder().gameSteamId(1).build()));
        when(springDataSteamGameReviewRepository.save(any()))
                .thenReturn(SteamGameReview.builder().gameSteamId(1).build());

        var result = steamGameReviewRepositoryImpl.save(SteamGameReview.builder().gameSteamId(1).build());

        assertNotNull(result.get());
        assertEquals(1, result.get().getGameSteamId());
    }

    @Test
    public void testSave_InexistentGame() {
        when(steamGameGateway.getSteamGameBySteamId(anyInt())).thenReturn(Optional.empty());

        var result = steamGameReviewRepositoryImpl.save(SteamGameReview.builder().gameSteamId(1).build());

        assertEquals(Optional.empty(), result);
    }
}