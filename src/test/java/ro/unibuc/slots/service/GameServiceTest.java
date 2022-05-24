package ro.unibuc.slots.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ro.unibuc.slots.repo.GameRepository;

@ExtendWith(SpringExtension.class)
public class GameServiceTest {
    @Mock
    private GameRepository mockGameRepository;

    @InjectMocks
    private GameService gameService;

    @Before
    public void init() {
        try (final var mocks = MockitoAnnotations.openMocks(this)) {
            System.out.println(mocks);
        }
        catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    @Test
    public void test_getWinningCombinations_returns6WinningCombinations() {
        Assertions.assertEquals(6, gameService.getWinningCombinations().size());
    }
}
