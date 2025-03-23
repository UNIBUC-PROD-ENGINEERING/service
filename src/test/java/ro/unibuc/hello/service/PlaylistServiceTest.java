package ro.unibuc.hello.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import ro.unibuc.hello.controller.PartyController;
import ro.unibuc.hello.data.PartyEntity;
import ro.unibuc.hello.data.SongEntity;
import ro.unibuc.hello.data.TaskEntity;
import ro.unibuc.hello.repositories.PartyRepository;
import ro.unibuc.hello.repositories.SongRepository;
import ro.unibuc.hello.repositories.TaskRepository;
import ro.unibuc.hello.service.PartyService;
import ro.unibuc.hello.service.YouTubeService;
import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

@ExtendWith(MockitoExtension.class)
public class PlaylistServiceTest {

    @Mock
    private PartyRepository partyRepository;

    @Mock
    private SongRepository songRepository;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private YouTubeService youTubeService;

    @Mock
    private PartyService partyService;

    @InjectMocks
    private PartyController partyController;

    private PartyEntity party;
    private SongEntity song;

    @BeforeEach
    void setUp() {
        party = new PartyEntity();
        party.setId("123");
        song = new SongEntity("Test Song", "Test Artist", "");
    }

    @Test
    public void testAddSongToParty_Success() {
        when(partyRepository.findById("123")).thenReturn(Optional.of(party));
        when(youTubeService.searchYouTube("Test Song", "Test Artist")).thenReturn("https://youtube.com/video123");
        when(songRepository.save(any())).thenReturn(song);

        ResponseEntity<?> response = partyController.addSongToParty("123", song);

        assertEquals(200, response.getStatusCode().value());
        verify(songRepository).save(any(SongEntity.class));
        verify(partyRepository).save(any(PartyEntity.class));
    }

    @Test
    public void testAddSongToParty_PartyNotFound() {
        when(partyRepository.findById("123")).thenReturn(Optional.empty());

        ResponseEntity<?> response = partyController.addSongToParty("123", song);

        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    public void testRemoveSongFromParty_Success() {
        party.addSong("456");
        song.setId("456");

        when(partyRepository.findById("123")).thenReturn(Optional.of(party));
        when(songRepository.findById("456")).thenReturn(Optional.of(song));

        ResponseEntity<?> response = partyController.removeSongFromParty("123", "456");

        assertEquals(204, response.getStatusCode().value());
        verify(songRepository).delete(song);
    }

    @Test
    public void testRemoveSongFromParty_SongNotFound() {
        party.addSong("456");

        when(partyRepository.findById("123")).thenReturn(Optional.of(party));
        when(songRepository.findById("456")).thenReturn(Optional.empty());

        ResponseEntity<?> response = partyController.removeSongFromParty("123", "456");

        assertEquals(404, response.getStatusCode().value(), "Expected 404 when the song is not found.");
        assertTrue(party.getPlaylistIds().contains("456"), "Song should still be in the playlist since deletion failed.");
    }

    @Test
    public void testCreateParty_Success() {
        when(partyRepository.save(any(PartyEntity.class))).thenReturn(party);
        ResponseEntity<?> response = ResponseEntity.ok(partyController.createParty(party));
        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    public void testDeleteParty_Success() {
        doNothing().when(partyRepository).deleteById("123");
        assertDoesNotThrow(() -> partyController.deleteParty("123"));
    }

    @Test
    public void testYouTubeSearch_Success() {
        when(youTubeService.searchYouTube("Test Song", "Test Artist")).thenReturn("https://youtube.com/video123");
        String result = youTubeService.searchYouTube("Test Song", "Test Artist");
        assertEquals("https://youtube.com/video123", result);
    }

    @Test
    public void testYouTubeSearch_NoResults() {
        when(youTubeService.searchYouTube("Unknown Song", "Unknown Artist")).thenReturn(null);
        String result = youTubeService.searchYouTube("Unknown Song", "Unknown Artist");
        assertNull(result);
    }

    @Test
    public void testYouTubeSearch_ApiFailure() {
        when(youTubeService.searchYouTube(anyString(), anyString())).thenThrow(new RuntimeException("API error"));
        Exception exception = assertThrows(RuntimeException.class, () -> youTubeService.searchYouTube("Error Song", "Error Artist"));
        assertEquals("API error", exception.getMessage());
    }

    @Test
    public void testAddSongToParty_ValidYouTubeLink() {
        when(partyRepository.findById("123")).thenReturn(Optional.of(party));
        when(youTubeService.searchYouTube("Test Song", "Test Artist")).thenReturn("https://youtube.com/video123");
        when(songRepository.save(any(SongEntity.class))).thenReturn(song);

        ResponseEntity<?> response = partyController.addSongToParty("123", song);

        assertEquals(200, response.getStatusCode().value());
        verify(songRepository).save(any(SongEntity.class));
        verify(partyRepository).save(any(PartyEntity.class));
    }

    @Test
    public void testAddSongToParty_InvalidYouTubeLink() {
        when(partyRepository.findById("123")).thenReturn(Optional.of(party));
        when(youTubeService.searchYouTube("Test Song", "Test Artist")).thenReturn(null);

        ResponseEntity<?> response = partyController.addSongToParty("123", song);

        assertEquals(400, response.getStatusCode().value());
        verify(songRepository, never()).save(any(SongEntity.class));
        verify(partyRepository, never()).save(any(PartyEntity.class));
    }


}