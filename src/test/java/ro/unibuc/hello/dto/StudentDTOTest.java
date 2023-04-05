// package ro.unibuc.hello.dto;

// import org.junit.jupiter.api.Assertions;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.springframework.boot.test.context.SpringBootTest;

// import static org.junit.jupiter.api.Assertions.*;

// @SpringBootTest
// class StudentDTOTest {
//     String id;
//     String name;
//     String email;
//     int age;
//     double[] grades;
//     StudentDTO studentDTO;
//     String studentDTOString;
//     @BeforeEach
//     void setUp() {
//         id = "642d31d5a249a13d08983f55";
//         name = "Ion Popescu";
//         email = "ion.popescu@gmail.com";
//         age = 22;
//         grades[0] = 9.0;
//         grades[1] = 8.0;
//         studentDTO = new StudentDTO();
//         studentDTO.setId(id);
//         studentDTO.setName(name);
//         studentDTO.setEmail(email);
//         studentDTO.setAge(age);
//         studentDTOString = "StudentDTO{" +
//                 "id='" + id + '\'' +
//                 ", name='" + name + '\'' +
//                 ", email='" + email + '\'' +
//                 ", age='" + age + '\'' +
//                 '}';
//     }

//     @Test
//     void getId() {
//         Assertions.assertEquals(id, studentDTO.getId());
//     }
    
//     @Test
//     void getName() {
//         Assertions.assertEquals(name, studentDTO.getName());
//     }

//     @Test
//     void getEmail() {
//         Assertions.assertEquals(email, studentDTO.getEmail());
//     }
    
//     @Test
//     void testToString() {
//         Assertions.assertEquals(studentDTOString, studentDTO.toString());
//     }
    
// }