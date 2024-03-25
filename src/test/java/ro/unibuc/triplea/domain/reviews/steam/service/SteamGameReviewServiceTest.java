package ro.unibuc.triplea.domain.reviews.steam.service;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;

import ro.unibuc.triplea.application.games.steam.dto.response.SteamGameResponse;
import ro.unibuc.triplea.application.reviews.steam.dto.request.SteamGameReviewRequest;
import ro.unibuc.triplea.application.reviews.steam.dto.response.SteamGameReviewResponse;
import ro.unibuc.triplea.domain.games.steam.exception.SteamGameNotFoundException;
import ro.unibuc.triplea.domain.games.steam.service.SteamGameService;
import ro.unibuc.triplea.domain.reviews.steam.model.entity.SteamGameReview;
import ro.unibuc.triplea.domain.reviews.steam.repository.SteamGameReviewRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class SteamGameReviewServiceTest {

    private SteamGameReviewRepository steamGameReviewRepository = mock(SteamGameReviewRepository.class);

    private SteamGameService steamGameService = mock(SteamGameService.class);

    private SteamGameReviewService steamGameReviewService = new SteamGameReviewService(steamGameReviewRepository, steamGameService);

    @Test
    public void testGetReviewsBySteamId() {
        int gameSteamId = 123;
        List<SteamGameReviewResponse> expected = new ArrayList<>();
        expected.add(SteamGameReviewResponse.builder()
                .gameSteamId(gameSteamId)
                .gameName("game1")
                .userName("user1")
                .reviewContent("review1")
                .build());
        expected.add(SteamGameReviewResponse.builder()
                .gameSteamId(gameSteamId)
                .gameName("game1")
                .userName("user2")
                .reviewContent("review2")
                .build());

        when(steamGameReviewRepository.findAllByGameSteamId(gameSteamId)).thenReturn(Optional.of(expected));

        Optional<List<SteamGameReviewResponse>> result = steamGameReviewService.getReviewsBySteamId(gameSteamId);

        assertEquals(expected.size(), result.orElse(new ArrayList<>()).size());
        verify(steamGameReviewRepository, times(1)).findAllByGameSteamId(gameSteamId);
    }

    @Test
    public void testGetReviewsByGameName() {
        String gameName = "game1";
        List<SteamGameReviewResponse> expected = new ArrayList<>();
        expected.add(SteamGameReviewResponse.builder()
                .gameSteamId(123)
                .gameName(gameName)
                .userName("user1")
                .reviewContent("review1")
                .build());
        expected.add(SteamGameReviewResponse.builder()
                .gameSteamId(456)
                .gameName(gameName)
                .userName("user2")
                .reviewContent("review2")
                .build());

        when(steamGameReviewRepository.findAllByGameName(gameName)).thenReturn(Optional.of(expected));

        Optional<List<SteamGameReviewResponse>> result = steamGameReviewService.getReviewsByGameName(gameName);

        assertEquals(expected.size(), result.orElse(new ArrayList<>()).size());
        verify(steamGameReviewRepository, times(1)).findAllByGameName(gameName);
    }

    @Test
    public void testGetReviewsByGameIdentifier_Id() {
        Integer steamId = 1;
        String gameIdentifier = steamId.toString();
        List<SteamGameReviewResponse> expected = new ArrayList<>();
        expected.add(SteamGameReviewResponse.builder()
                .gameSteamId(steamId)
                .gameName("game1")
                .userName("user1")
                .reviewContent("review1")
                .build());
        expected.add(SteamGameReviewResponse.builder()
                .gameSteamId(steamId)
                .gameName("game1")
                .userName("user2")
                .reviewContent("review2")
                .build());

        when(steamGameReviewRepository.findAllByGameSteamId(steamId)).thenReturn(Optional.of(expected));

        Optional<List<SteamGameReviewResponse>> result = steamGameReviewService
                .getReviewsByGameIdentifier(gameIdentifier);

        assertEquals(expected.size(), result.orElse(new ArrayList<>()).size());
    }

    @Test
    public void testGetReviewsByGameIdentifier_Id_GameNotFound() {
        Integer steamId = 1;
        String gameIdentifier = steamId.toString();

        when(steamGameReviewRepository.findAllByGameSteamId(steamId)).thenReturn(Optional.empty());

        assertThrows(SteamGameNotFoundException.class,
                () -> steamGameReviewService.getReviewsByGameIdentifier(gameIdentifier));
    }

    @Test
    public void testGetReviewsByGameIdentifier_GameName() {
        String gameName = "game1";
        List<SteamGameReviewResponse> expected = new ArrayList<>();
        expected.add(SteamGameReviewResponse.builder()
                .gameSteamId(1)
                .gameName(gameName)
                .userName("user1")
                .reviewContent("review1")
                .build());
        expected.add(SteamGameReviewResponse.builder()
                .gameSteamId(2)
                .gameName(gameName)
                .userName("user2")
                .reviewContent("review2")
                .build());

        when(steamGameReviewRepository.findAllByGameName(gameName)).thenReturn(Optional.of(expected));

        Optional<List<SteamGameReviewResponse>> result = steamGameReviewService.getReviewsByGameIdentifier(gameName);

        assertEquals(expected.size(), result.orElse(new ArrayList<>()).size());
    }

    @Test
    public void testAddReview() {

        SteamGameReviewRequest gameReviewRequest = SteamGameReviewRequest.builder()
                .gameSteamId(1)
                .reviewContent("review1")
                .build();
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("user1");
        when(steamGameService.getGameBySteamId(anyInt())).thenReturn(Optional.of(SteamGameResponse.builder()
                .gameName("game1")
                .gameSteamId(1)
                .build()));

        SteamGameReviewResponse expectedResponse = SteamGameReviewResponse.builder()
                .gameSteamId(1)
                .gameName("game1")
                .userName("user1")
                .reviewContent("review1")
                .build();
        when(steamGameReviewRepository.save(any(SteamGameReview.class))).thenReturn(Optional.of(expectedResponse));

        Optional<SteamGameReviewResponse> result = steamGameReviewService.addReview(gameReviewRequest, userDetails);

        assertTrue(result.isPresent());
        assertEquals(expectedResponse, result.get());
    }

    @Test
    public void testAddReview_NonexistentGame() {

        SteamGameReviewRequest gameReviewRequest = SteamGameReviewRequest.builder()
                .gameSteamId(1)
                .reviewContent("review1")
                .build();
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("user1");
        when(steamGameService.getGameBySteamId(anyInt())).thenReturn(Optional.empty());

        assertThrows(SteamGameNotFoundException.class,
                () -> steamGameReviewService.addReview(gameReviewRequest, userDetails));
    }

    @Test
    public void testGetReviewsByUserName() {
        String username = "user1";
        List<SteamGameReviewResponse> reviews = new ArrayList<>();
        reviews.add(SteamGameReviewResponse.builder()
                .gameSteamId(1)
                .gameName("game1")
                .userName("user1")
                .reviewContent("review1")
                .build());
        reviews.add(SteamGameReviewResponse.builder()
                .gameSteamId(2)
                .gameName("game2")
                .userName("user1")
                .reviewContent("review2")
                .build());
        when(steamGameReviewRepository.findAllByUserName(username)).thenReturn(Optional.of(reviews));

        List<SteamGameReviewResponse> expectedResponse = new ArrayList<>();
        expectedResponse.add(SteamGameReviewResponse.builder()
                .gameSteamId(1)
                .gameName("game1")
                .userName("user1")
                .reviewContent("review1")
                .build());
        expectedResponse.add(SteamGameReviewResponse.builder()
                .gameSteamId(2)
                .gameName("game2")
                .userName("user1")
                .reviewContent("review2")
                .build());

        Optional<List<SteamGameReviewResponse>> result = steamGameReviewService.getReviewsByUserName(username);

        assertTrue(result.isPresent());
        assertEquals(expectedResponse, result.get());
    }
}
