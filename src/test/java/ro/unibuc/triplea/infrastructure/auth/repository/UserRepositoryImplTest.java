package ro.unibuc.triplea.infrastructure.auth.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ro.unibuc.triplea.domain.auth.model.entity.meta.User;
import ro.unibuc.triplea.infrastructure.auth.fixtures.UserFixtures;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserRepositoryImplTest {

    @Mock
    private SpringDataUserRepository springDataUserRepository;

    @InjectMocks
    private UserRepositoryImpl userRepositoryImpl;

    private final User user = UserFixtures.user("username");

    @Test
    void findByEmailShouldDelegateAndReturnResult() {
        when(springDataUserRepository.findByUsername("username")).thenReturn(Optional.of(user));

        Optional<User> result = userRepositoryImpl.findByEmail("username");

        assertThat(result).isPresent().containsSame(user);
        verify(springDataUserRepository, times(1)).findByUsername("username");
    }

    @Test
    void saveShouldDelegateAndReturnResult() {
        when(springDataUserRepository.save(any(User.class))).thenReturn(user);

        User result = userRepositoryImpl.save(user);

        assertThat(result).isSameAs(user);
        verify(springDataUserRepository, times(1)).save(user);
    }
}