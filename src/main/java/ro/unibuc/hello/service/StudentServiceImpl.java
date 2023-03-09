package ro.unibuc.hello.service;

import org.springframework.stereotype.Service;
import ro.unibuc.hello.dto.SubjectGrade;
import ro.unibuc.hello.models.CatalogEntity;
import ro.unibuc.hello.models.StudentEntity;
import ro.unibuc.hello.repositories.CatalogRepository;
import ro.unibuc.hello.repositories.StudentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    private final CatalogRepository catalogRepository;


    private final StudentRepository studentRepository;

    public StudentServiceImpl(CatalogRepository catalogRepository, StudentRepository studentRepository) {
        this.catalogRepository = catalogRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public List<SubjectGrade> getGradesByStudentId(String studentId) {
        Optional<StudentEntity> student = studentRepository.findById(studentId);
        CatalogEntity catalog = new CatalogEntity();
        if (student.isPresent()) {
            catalog = catalogRepository.findByStudent(student.get());
        }

        return catalog.grades;
    }
}
