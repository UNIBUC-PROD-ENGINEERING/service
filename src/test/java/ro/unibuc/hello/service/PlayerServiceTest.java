package ro.unibuc.hello.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ro.unibuc.hello.data.InformationEntity;
import ro.unibuc.hello.data.InformationRepository;
import ro.unibuc.hello.data.PlayerEntity;
import ro.unibuc.hello.data.PlayerRepository;
import ro.unibuc.hello.dto.Greeting;
import ro.unibuc.hello.exception.EntityNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class PlayerServiceTest {

    @Mock
    PlayerRepository mockPlayerRepository;

    @InjectMocks
    PlayerService playerService;

    @Test
    public void test_GetPlayerTeam(){
        String name="Stephen Curry";
        PlayerEntity newPlayer=new PlayerEntity("101",name,"PHX",12.0,12.0,12.0);
        when(mockPlayerRepository.findByName(name)).thenReturn(newPlayer);

        String team=playerService.getPlayerTeam(name);

        assertEquals(newPlayer.getTeam(), team);
    }
    
}
