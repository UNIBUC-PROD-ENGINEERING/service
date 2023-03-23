package ro.unibuc.hello.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ro.unibuc.hello.dto.TeacherDto;
import ro.unibuc.hello.models.TeacherEntity;
import ro.unibuc.hello.repositories.TeacherRepository;

import java.util.Optional;

@Service
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;
    private final ModelMapper modelMapper;

    public TeacherServiceImpl(TeacherRepository teacherRepository,
                              ModelMapper modelMapper) {
        this.teacherRepository = teacherRepository;
        this.modelMapper = modelMapper;
    }

    //TODO Validari
    @Override
    public TeacherEntity updateTeacherData(TeacherDto dto, String teacherId) {
        Optional<TeacherEntity> teacher = teacherRepository.findById(teacherId);

        if (teacher.isPresent()) {
            modelMapper.map(dto, teacher.get());
            teacherRepository.save(teacher.get());
        }

        return teacher.get();
    }
}
