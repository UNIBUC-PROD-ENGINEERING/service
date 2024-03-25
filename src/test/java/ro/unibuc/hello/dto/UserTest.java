package ro.unibuc.hello.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ro.unibuc.hello.dto.UserDto;
import ro.unibuc.hello.data.UserEntity;

class UserTest {

    String firstName = "Wesley";
    String lastName = "Lopes";
    Integer age = 35;
    String userName = "wesley80";
    UserDto userDto = new UserDto(firstName, lastName, age, userName);

    @Test
    void testGetters() {
        Assertions.assertEquals(firstName, userDto.getFirstName());
        Assertions.assertEquals(lastName, userDto.getLastName());
        Assertions.assertEquals(age, userDto.getAge());
        Assertions.assertEquals(userName, userDto.getUserName());
    }

    @Test
    void testSetters(){
        String newFirstName = "Adailton";
        userDto.setFirstName(newFirstName);
        Assertions.assertEquals(newFirstName, userDto.getFirstName());

        Integer newAge = 40;
        userDto.setAge(newAge);
        Assertions.assertEquals(newAge, userDto.getAge());
    }

    @Test
    void testToStringMethod() {
        UserEntity userEntity = new UserEntity(lastName, firstName, age, userName);
    
        String expectedToString = "User[lastName='Lopes', firstName='Wesley', age='35', userName='wesley80']";
    
        Assertions.assertEquals(expectedToString, userEntity.toString());
    }

    @Test
    void testEquality() {
        UserDto sameUserDto = new UserDto(firstName, lastName, age, userName);

        Assertions.assertEquals(userDto, sameUserDto);
    }

    @Test
    void testInequality() {
        UserDto differentUserDto = new UserDto("Mike", "Temwanjera", 45, "mike");

        Assertions.assertNotEquals(userDto, differentUserDto);
    }

    @Test
    void testNullFields() {
        UserDto nullUserDto = new UserDto(null, null, null, null);

        Assertions.assertNull(nullUserDto.getFirstName());
        Assertions.assertNull(nullUserDto.getLastName());
        Assertions.assertNull(nullUserDto.getAge());
        Assertions.assertNull(nullUserDto.getUserName());
    }

}