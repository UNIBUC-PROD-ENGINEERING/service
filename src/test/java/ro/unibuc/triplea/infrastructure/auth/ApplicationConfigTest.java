package ro.unibuc.triplea.infrastructure.auth;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;
import ro.unibuc.triplea.domain.auth.repository.UserRepository;
import ro.unibuc.triplea.infrastructure.auth.fixtures.UserFixtures;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ApplicationConfigTest {

    private final AuthenticationConfiguration authenticationConfiguration = mock(AuthenticationConfiguration.class);
    private final AuthenticationManager authenticationManager = mock(AuthenticationManager.class);

    private final UserRepository userRepository = mock(UserRepository.class);
    private final ApplicationConfig applicationConfig = new ApplicationConfig(userRepository);

    @Test
    public void givenUsername_whenUserDetailsServiceAndUsernameEmpty_thenReturnUserDetailsService() {
        when(userRepository.findByEmail("username")).thenReturn(Optional.of(UserFixtures.user("username")));

        assertThat(applicationConfig.userDetailsService()).isInstanceOf(UserDetailsService.class);
    }

    @Test
    public void givenUsername_whenUserDetailsServiceAndUserNotFound_thenThrowException() {
        when(userRepository.findByEmail("username")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> applicationConfig.userDetailsService().loadUserByUsername("username"));
    }

    @Test
    public void whenAuthenticationManager_thenReturnAuthenticationManager() throws Exception {
        when(authenticationConfiguration.getAuthenticationManager()).thenReturn(authenticationManager);

        assertThat(applicationConfig.authenticationManager(authenticationConfiguration)).isInstanceOf(AuthenticationManager.class);
    }

    @Test
    public void whenAuthenticationProvider_thenReturnDaoAuthenticationProvider() {
        assertThat(applicationConfig.authenticationProvider()).isInstanceOf(DaoAuthenticationProvider.class);
    }

    @Test
    public void whenAuthenticationEncoder_thenReturnBCryptPasswordEncoder() {
        assertThat(applicationConfig.passwordEncoder()).isInstanceOf(BCryptPasswordEncoder.class);
    }

    @Test
    public void whenRestTemplate_thenReturnRestTemplate() {
        assertThat(applicationConfig.restTemplate()).isInstanceOf(RestTemplate.class);
    }

}
