package ro.unibuc.hello.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class UserDtoTest {

    @InjectMocks
    UserDto userDtoMock = Mockito.mock(UserDto.class);
    UserDto userDto;

    String testId = "123345test";
    String testFirstName = "testFirstName";
    String testLastName = "testLastName";
    String testPassword = "testPassword";
    String testEmail = "test@emaill.tst";

    @BeforeEach
    void setup() {
        userDto = new UserDto("1", "1", "1", "1");
    }

    @Test
    void WHEN_id_getter_is_called_THEN_return_correct_id_value(){
        userDtoMock.getId();
        verify(userDtoMock, times(1)).getId();
    }

    @Test
    void WHEN_first_name_getter_is_called_THEN_return_correct_first_name_value(){
        Assertions.assertSame("1", userDto.getFirstName());
    }

    @Test
    void WHEN_last_name_getter_is_called_THEN_return_correct_last_name_value(){
        Assertions.assertSame("1", userDto.getLastName());
    }

    @Test
    void WHEN_password_getter_is_called_THEN_return_correct_password_value(){
        Assertions.assertSame("1", userDto.getPassword());
    }

    @Test
    void WHEN_email_getter_is_called_THEN_return_correct_email_value(){
        Assertions.assertSame("1", userDto.getEmail());
    }

    @Test
    void WHEN_id_is_set_THEN_set_correct_id_value(){
        userDto.setId(testId);
        Assertions.assertSame(testId, userDto.getId());
    }

    @Test
    void WHEN_first_name_is_set_THEN_set_correct_first_name_value(){
        userDto.setFirstName(testFirstName);
        Assertions.assertSame(testFirstName, userDto.getFirstName());
    }

    @Test
    void WHEN_last_name_is_set_THEN_set_correct_last_name_value(){
        userDto.setLastName(testLastName);
        Assertions.assertSame(testLastName, userDto.getLastName());
    }

    @Test
    void WHEN_password_is_set_THEN_set_correct_password_value(){
        userDto.setPassword(testPassword);
        Assertions.assertSame(testPassword, userDto.getPassword());
    }

    @Test
    void WHEN_email_is_set_THEN_set_correct_email_value(){
        userDto.setEmail(testEmail);
        Assertions.assertSame(testEmail, userDto.getEmail());
    }


}