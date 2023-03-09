package ro.unibuc.hello.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ro.unibuc.hello.dto.StudentDto;
import ro.unibuc.hello.models.StudentEntity;
import ro.unibuc.hello.repositories.StudentRepository;

@Service
public class StudentServiceImpl implements StudentService {

    private final ModelMapper modelMapper;
    private final StudentRepository studentRepository;

    public StudentServiceImpl(ModelMapper modelMapper, StudentRepository studentRepository) {
        this.modelMapper = modelMapper;
        this.studentRepository = studentRepository;
    }

    @Override
    public StudentEntity addStudent(StudentDto student) {
        StudentEntity newStudent = modelMapper.map(student, StudentEntity.class);
        studentRepository.save(newStudent);
        return newStudent;
    }
}
