package ro.unibuc.hello.service;

import org.springframework.stereotype.Service;
import ro.unibuc.hello.repositories.*;
import ro.unibuc.hello.models.CatalogEntity;
import ro.unibuc.hello.models.StudentEntity;
import ro.unibuc.hello.models.TeacherEntity;
import ro.unibuc.hello.dto.SubjectGradeDto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class MockDbService {
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final CatalogRepository catalogRepository;

    public MockDbService(TeacherRepository teacherRepository,
                         StudentRepository studentRepository,
                         CatalogRepository catalogRepository) {
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
        this.catalogRepository = catalogRepository;
    }

    /**
     * Initialize database with one teacher, 3 students, and 3 catalogues with 1 grade each.
     */
    public void mockDatabase() {
        teacherRepository.deleteAll();
        TeacherEntity teacher = new TeacherEntity("Profesor", "Profesorel", "Informatica");
        teacherRepository.save(teacher);

        studentRepository.deleteAll();
        List<StudentEntity> studentsToAdd = new ArrayList<>();
        studentsToAdd.add(new StudentEntity("Georgica", "Ionescu", "461", LocalDate.of(2000, 11, 29)));
        studentsToAdd.add(new StudentEntity("Petruta", "Popescu", "462", LocalDate.of(2000, 12, 12)));
        studentsToAdd.add(new StudentEntity("Ion", "Paladi", "461", LocalDate.of(2000, 10, 17)));
        studentRepository.saveAll(studentsToAdd);

        Random rand = new Random();

        catalogRepository.deleteAll();
        List<CatalogEntity> cataloguesToAdd = new ArrayList<>();
        for (StudentEntity student : studentsToAdd) {
            CatalogEntity catalog = new CatalogEntity();
            catalog.setStudent(student);
            catalog.addGrade(new SubjectGradeDto(teacher, rand.nextInt(10) + 1, LocalDate.now()));
            cataloguesToAdd.add(catalog);
        }
        catalogRepository.saveAll(cataloguesToAdd);
    }


}
