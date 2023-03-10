package ro.unibuc.hello.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ro.unibuc.hello.dto.StudentDto;
import ro.unibuc.hello.models.StudentEntity;
import ro.unibuc.hello.repositories.StudentRepository;
import ro.unibuc.hello.dto.SubjectGradeDto;
import ro.unibuc.hello.models.CatalogEntity;
import ro.unibuc.hello.repositories.CatalogRepository;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    private final ModelMapper modelMapper;
    private final StudentRepository studentRepository;

    private final CatalogRepository catalogRepository;


    public StudentServiceImpl(ModelMapper modelMapper,
                              CatalogRepository catalogRepository,
                              StudentRepository studentRepository) {
        this.modelMapper = modelMapper;
        this.catalogRepository = catalogRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public StudentEntity addStudent(StudentDto student) {
        StudentEntity newStudent = modelMapper.map(student, StudentEntity.class);
        studentRepository.save(newStudent);
        return newStudent;
    }

    public List<SubjectGradeDto> getGradesByStudentId(String studentId) {
        Optional<StudentEntity> student = studentRepository.findById(studentId);
        CatalogEntity catalog = new CatalogEntity();
        if (student.isPresent()) {
            catalog = catalogRepository.findByStudent(student.get());
        }

        return catalog.getGrades();
    }
}
