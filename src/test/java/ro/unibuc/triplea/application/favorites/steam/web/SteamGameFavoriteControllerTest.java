package ro.unibuc.triplea.application.favorites.steam.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import ro.unibuc.triplea.application.favorites.steam.dto.request.SteamGameFavoriteRequest;
import ro.unibuc.triplea.application.favorites.steam.dto.response.SteamGameFavoriteResponse;
import ro.unibuc.triplea.domain.favorites.steam.service.SteamGameFavoriteService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class SteamGameFavoriteControllerTest {

    @Mock
    private SteamGameFavoriteService steamGameFavoriteService;

    @InjectMocks
    private SteamGameFavoriteController steamGameFavoriteController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetFavoritesByUserId() {
        String userName = "testUser";
        List<SteamGameFavoriteResponse> favoritesList = Collections.singletonList(new SteamGameFavoriteResponse(/* pass necessary parameters */));
        Optional<List<SteamGameFavoriteResponse>> optionalFavorites = Optional.of(favoritesList);
        when(steamGameFavoriteService.getFavoritesByUserName(userName)).thenReturn(optionalFavorites);

        ResponseEntity<?> response = steamGameFavoriteController.getFavoritesByUserId(userName);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(favoritesList, response.getBody());
        verify(steamGameFavoriteService, times(1)).getFavoritesByUserName(userName);
    }

    @Test
    public void testAddFavorite() {
        SteamGameFavoriteRequest favoriteRequest = new SteamGameFavoriteRequest(/* pass necessary parameters */);
        UserDetails userDetails = new User("username", "password", Collections.emptyList());
        SteamGameFavoriteResponse addedFavoriteResponse = new SteamGameFavoriteResponse(/* pass necessary parameters */);
        Optional<SteamGameFavoriteResponse> optionalAddedFavorite = Optional.of(addedFavoriteResponse);
        when(steamGameFavoriteService.addFavorite(favoriteRequest, userDetails)).thenReturn(optionalAddedFavorite);

        ResponseEntity<?> response = steamGameFavoriteController.addFavorite(favoriteRequest, userDetails);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(addedFavoriteResponse, response.getBody());
        verify(steamGameFavoriteService, times(1)).addFavorite(favoriteRequest, userDetails);
    }
}
