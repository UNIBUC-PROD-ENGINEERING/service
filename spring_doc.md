## Spring and Spring Boot

Spring is a lightweight framework providing a sophisticated environment for programming and a configuration model for Java-based applications. Spring Boot, on the other hand, is a Java-based framework specifically tailored for creating stand-alone, Spring-based applications.

## Inversion of control

It is a software design principle that implies transferring control for certain portions of a program to a container or framework.

## Dependency Injection

Dependency injection is a pattern used for implementing IoC. The DI (Dependency Injection) container has the control, being responsible for managing dependencies between objects and providing them where they are requested.

DI example respecting IoC principle:

```java
public class Student {
 private final University university;
 public Student (University university) {
    this.university = university;
 }
```

DI example not respecting IoC principle:

```java
public class Student {
private final University university;
public Student () {
    this.university = new University ("Unibuc");
}
```


**Resources:**

<https://www.baeldung.com/inversion-control-and-dependency-injection-in-spring>


## Spring Boot for CRUD

Down below we implement some CRUD operations with the help of Spring Boot.

First of all, let’s create an entity class which matches a table in the database and its’ fields.

```java
@Document(collection = "students")
public class Student {
    @Id
    private long id;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String faculty;
}
```


We create a repository class containing methods through which we interact with the database. Here we have the basic operations of: C - create, R - read, U - update, D - delete.

```java
import ...

public interface StudentRepository extends MongoRepository<Student, Long> {
}
```


We create a service class. It handles all the data processing and sends the result to the controller for a GET request or inserts it in the data base in the case of  a PUT request. The service class uses the methods from the repository.


```javascript
import ...

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    StudentService(StudentRepository repo) {
        this.studentRepository = repo;
    }

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
        final Optional<Student> existingStudent = studentRepository.findById(id);

        if (existingStudent.isPresent()) {
            final Student updatedStudent = existingStudent.get();
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


The last one is the Controller class. It calls the appropiate methods from Service depending on the request sent by the client. Afterwards, it sends the result back to the client.

```java
import ...

@RestController
@RequestMapping("/api")
public class StudentController {

    private final StudentService studentService;
    StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // Retrieve all students
    @GetMapping("/students")
    public ResponseEntity<List<Student>> getAllStudents() {
        final List<Student> students = studentService.getAllStudents();
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    // Retrieve a student by ID
    @GetMapping("/students/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        return studentService.getStudentById(id)
                .map(student -> new ResponseEntity<>(student, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Create a new student
    @PostMapping("/students")
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        final Student newStudent = studentService.createStudent(student);
        return new ResponseEntity<>(newStudent, HttpStatus.CREATED);
    }

    // Update a student by ID
    @PutMapping("/students/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student student) {
        return studentService.updateStudent(id, student)
                .map(updatedStudent -> new ResponseEntity<>(updatedStudent, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Delete a student by ID
    @DeleteMapping("/students/{id}")
    public ResponseEntity<HttpStatus> deleteStudent(@PathVariable Long id) {
        return studentService.deleteStudent(id)
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
```

Resources:

<https://www.youtube.com/watch?v=m-L-r862J-E&list=PLOk4ziGG9MBdlyxIDw5wYvj6QZTQ22wvK>