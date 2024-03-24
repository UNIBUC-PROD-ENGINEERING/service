package ro.unibuc.triplea.application.reviews.steam.web;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import ro.unibuc.triplea.application.games.steam.dto.response.SteamGameResponse;
import ro.unibuc.triplea.application.reviews.steam.dto.request.SteamGameReviewRequest;
import ro.unibuc.triplea.application.reviews.steam.dto.response.SteamGameReviewResponse;
import ro.unibuc.triplea.domain.reviews.steam.service.SteamGameReviewService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class SteamGameReviewControllerTest {

    private final SteamGameReviewService steamGameReviewService = Mockito.mock(SteamGameReviewService.class);

    private final SteamGameReviewController steamGameReviewController = new SteamGameReviewController(
            steamGameReviewService);

    @Test
    public void testGetReviewsBySteamId() {
        String gameId = "123";

        List<SteamGameReviewResponse> reviews = Arrays.asList(
                SteamGameReviewResponse.builder()
                        .userName("user1")
                        .reviewContent("Great game!")
                        .build(),
                SteamGameReviewResponse.builder()
                        .userName("user2")
                        .reviewContent("Awesome graphics!")
                        .build());

        when(steamGameReviewService.getReviewsByGameIdentifier(gameId)).thenReturn(Optional.of(reviews));

        ResponseEntity<?> responseEntity = steamGameReviewController.getReviewsBySteamId(gameId);

        assertEquals(ResponseEntity.ok(reviews), responseEntity);

    }

    @Test
    public void testGetReviewsBySteamId_SteamIdNotFound() {
        String gameId = "456";

        when(steamGameReviewService.getReviewsByGameIdentifier(gameId)).thenReturn(Optional.empty());

        ResponseEntity<?> responseEntity = steamGameReviewController.getReviewsBySteamId(gameId);

        assertEquals(ResponseEntity.notFound().build(), responseEntity);
    }

    @Test
    public void testGetReviewsByUserId() {
        String userName = "user1";

        List<SteamGameReviewResponse> reviews = Arrays.asList(
                SteamGameReviewResponse.builder()
                        .userName("user1")
                        .reviewContent("Great game!")
                        .build(),
                SteamGameReviewResponse.builder()
                        .userName("user1")
                        .reviewContent("Awesome graphics!")
                        .build());

        when(steamGameReviewService.getReviewsByUserName(userName)).thenReturn(Optional.of(reviews));

        ResponseEntity<?> responseEntity = steamGameReviewController.getReviewsByUserId(userName);

        assertEquals(ResponseEntity.ok(reviews), responseEntity);
    }

    @Test
    public void testGetReviewsByUserId_UserNotFound() {
        String userName = "user3";

        when(steamGameReviewService.getReviewsByUserName(userName)).thenReturn(Optional.empty());

        ResponseEntity<?> responseEntity = steamGameReviewController.getReviewsByUserId(userName);

        assertEquals(ResponseEntity.notFound().build(), responseEntity);
    }

    @Test
    public void testAddReview() {
        SteamGameReviewRequest reviewRequest = SteamGameReviewRequest.builder()
                .gameSteamId(0)
                .reviewContent("Great game!")
                .build();

        UserDetails userDetails = Mockito.mock(UserDetails.class);

        SteamGameReviewResponse reviewResponse = SteamGameReviewResponse.builder()
                .gameSteamId(0)
                .gameName("Game")
                .userName("user1")
                .reviewContent("Great game!")
                .build();

        when(steamGameReviewService.addReview(reviewRequest, userDetails)).thenReturn(Optional.of(reviewResponse));

        ResponseEntity<?> responseEntity = steamGameReviewController.addReview(reviewRequest, userDetails);

        assertEquals(ResponseEntity.ok(reviewResponse), responseEntity);
    }

    @Test
    public void testAddReview_BadRequest() {
        SteamGameReviewRequest reviewRequest = SteamGameReviewRequest.builder()
                .gameSteamId(0)
                .reviewContent(null) // Set review content as null to simulate a bad request
                .build();

        UserDetails userDetails = Mockito.mock(UserDetails.class);

        ResponseEntity<?> responseEntity = steamGameReviewController.addReview(reviewRequest, userDetails);

        assertEquals(ResponseEntity.badRequest().build(), responseEntity);
    }
}
