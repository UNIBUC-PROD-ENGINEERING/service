## Inversion of control

Este un principiu de programare care se refera la transferarea controlului pentru

anumite portiuni dintr-un program catre un container sau un framework.

## Dependency Injection

Dependency injection este un pattern folosit pentru a implementa IoC, deoarece

controlul revine dependetelor unui obiect.


Un exemplu de dependency injection care respecta principiul IoC:

```java
public class Student {
 University university;
 public Student (University university) {
    this.university = university;
 }
```

Un exemplu de dependency injection care nu respecta principiu IoC:

```java
public class Student {
University university;
public Student () {
    this.university = new University ("Unibuc");
}
```


**Linkuri utile pt IoC si dependency injection:**

<https://www.youtube.com/watch?v=EPv9-cHEmQw>

<https://www.educative.io/answers/what-is-inversion-of-control>

<https://www.baeldung.com/inversion-control-and-dependency-injection-in-spring>


## Spring Boot

Vom prezenta mai jos implementarea catorva operatii de tip CRUD cu ajutorul Spring Boot.

Se creeaza o entitate, care reprezinta structura tabelului care va fi salvat in baza de date.

```java
@Entity
@Table(name = "student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Getter
    @Setter
    @Column(name = "name")
    private String name;

    @Getter
    @Setter
    @Column(name = "faculty")
    private String faculty;
}
```


Se creeaza o clasa de tip repository, care ofera o interfata cu metode prin care se interactioneaza cu baza de date. Aceasta pune la dispozitie operatiile elementare de C - create, R - read, U - update, D - delete.

```java
import ...

import com.startdemo.demo.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
```


Se creeaza o clasa de tip service; aici sunt efectuate toate prelucrarile de date. Rezultatul este apoi trimis catre controller in cazul unei cereri de tip GET sau inserat in baza de date in cazul unei cereri de tip PUT. Service se foloseste de operatiile puse la dispozitie de repository.

```javascript
import ...

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }

    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    public Optional<Student> updateStudent(Long id, Student student) {
        Optional<Student> existingStudent = studentRepository.findById(id);

        if (existingStudent.isPresent()) {
            Student updatedStudent = existingStudent.get();
            updatedStudent.setName(student.getName());
            updatedStudent.setFaculty(student.getFaculty());
            return Optional.of(studentRepository.save(updatedStudent));
        } else {
            return Optional.empty();
        }
    }

    public boolean deleteStudent(Long id) {
        try {
            studentRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
```


Se creeaza o clasa de tip Controller, care apeleaza metodele potrivite (din Service) pentru cererea primita de la frontend. Tot el returneaza rezultatul primit de catre metoda din service si il trimite la frontend.

```java
import ...

@RestController
@RequestMapping("/api")
public class StudentController {

    @Autowired
    private StudentService studentService;

    // Return all students
    @GetMapping("/students")
    public List<Student> getAllStudents() {
        List<Student> students = studentService.getAllStudents();
        return students;
    }

    // Return a student by ID
    @GetMapping("/students/{id}")
    public Optional<Student> getStudentById(@PathVariable Long id) {
        return studentService.getStudentById(id);
    }

    // Create a new student
    @PostMapping("/students")
    public Student createStudent(@RequestBody Student student) {
        Student newStudent = studentService.createStudent(student);
        return newStudent;
    }

    // Update a student by ID
    @PutMapping("/students/{id}")
    public Student updateStudent(@PathVariable Long id, @RequestBody Student student) {
        return studentService.updateStudent(id, student);
    }

    // Delete a student by ID
    @DeleteMapping("/students/{id}")
    public Student deleteStudent(@PathVariable Long id) {
        return studentService.deleteStudent(id);
    }
}
```

Linkuri utile:

<https://www.youtube.com/watch?v=IucFDX3RO9U>

<https://www.youtube.com/watch?v=9SGDpanrc8U>

<https://www.youtube.com/watch?v=vtPkZShrvXQ&pp=ygUdU3ByaW5nIHR1dG9yaWFsIGZvciBiZWdpbm5lcnM%3D>

<https://docs.spring.io/spring-framework/reference/index.html>

<https://www.youtube.com/watch?v=Ch163VfHtvA&list=PLsyeobzWxl7oA8QOlMtQsRT_I7Rx2hoX4>



