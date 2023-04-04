package ro.unibuc.hello.service;

import org.apache.commons.lang3.ObjectUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ro.unibuc.hello.dto.ResponseDto;
import ro.unibuc.hello.dto.StudentDto;
import ro.unibuc.hello.dto.StudentGradeDto;
import ro.unibuc.hello.exception.EntityNotFoundException;
import ro.unibuc.hello.exception.InvalidModelException;
import ro.unibuc.hello.helpers.RegexFormatters;
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

    private Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);


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
        if (StudentEntity.validateStudent(newStudent)) {
            return studentRepository.save(newStudent);
        } else if (newStudent == null) {
                logger.error("Invalid model provided", new InvalidModelException(null));
                return null;
        } else {
                logger.error("Invalid model provided", new InvalidModelException(newStudent.toString()));
                return null;
        }
    }


    @Override
    public ResponseDto addGrade(StudentGradeDto dto) {
        studentRepository.findById(dto.getStudentId())
                .ifPresentOrElse(
                        student -> handleStudent(student, dto),
                        () -> {
                            throw new EntityNotFoundException(dto.getStudentId());
                        }
                );
        return new ResponseDto(true, "Grade added successfully");
    }

    private void handleStudent(StudentEntity student, StudentGradeDto dto) {
        CatalogEntity catalog = catalogRepository.findByStudent(student);
        if (catalog == null) {
            catalog = new CatalogEntity();
            catalog.setStudent(student);
        }
        catalog.addGrade(dto.getGrade());
        catalogRepository.save(catalog);
    }

    public List<SubjectGradeDto> getGradesByStudentId(String studentId) {
        Optional<StudentEntity> student = studentRepository.findById(studentId);
        if (student.isPresent()) {
            CatalogEntity catalog = catalogRepository.findByStudent(student.get());
            if (catalog != null) {
                return catalog.getGrades();
            }
        }
        return null;
    }
}
