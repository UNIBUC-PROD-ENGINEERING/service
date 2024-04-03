package ro.unibuc.hello.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ro.unibuc.hello.data.PlayerEntity;
import ro.unibuc.hello.data.PlayerRepository;
import ro.unibuc.hello.data.TeamEntity;
import ro.unibuc.hello.data.TeamRepository;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class TeamServiceTest {
    @Mock
    TeamRepository teamRepository;

    @Mock
    PlayerRepository playerRepository;

    @InjectMocks
    TeamService teamService;

    @Test
    public void test_GetBestPlayer(){
        String teamName = "Los Angeles Lakers";
        TeamEntity team = new TeamEntity("1", teamName, Arrays.asList(32), 1947, "Frank Vogel");
        PlayerEntity playerLeBron = new PlayerEntity("32", "LeBron James", teamName, 28.0, 7.4, 7.4);

        when(teamRepository.findByName(teamName)).thenReturn(team);
        when(playerRepository.findById("32")).thenReturn(Optional.of(playerLeBron));

        String result = teamService.getBestPlayer(teamName);
        
        assertEquals(result, "Best player: " + playerLeBron);

    }
}
