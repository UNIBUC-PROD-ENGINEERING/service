package ro.unibuc.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.unibuc.hello.dto.ApplicationDto;
import ro.unibuc.hello.dto.ProjectDto;
import ro.unibuc.hello.dto.UpdateProjectDto;
import ro.unibuc.hello.entity.ApplicationEntity;
import ro.unibuc.hello.data.ApplicationRepository;
import ro.unibuc.hello.entity.ProjectEntity;
import ro.unibuc.hello.entity.UserEntity;
import ro.unibuc.hello.exception.ProjectNotFoundException;

import java.util.List;
import java.util.Optional;

@Component
public class ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    public ApplicationEntity apply(ApplicationDto applicationDto) {
        ApplicationEntity applicationEntity = new ApplicationEntity();

        applicationEntity.setUserId(applicationDto.getUserId());
        applicationEntity.setProjectId(applicationDto.getProjectId());
        // pending
        applicationEntity.setStatus(0);

        return applicationRepository.save(applicationEntity);
    }

    public List<ApplicationEntity> getAppsByProjectId(String id) {
        return applicationRepository.findByProjectId(id);
    }


    public void acceptApplicationById(String id) {
        Optional<ApplicationEntity> optionalApplicationEntity = applicationRepository.findById(id);
        if (optionalApplicationEntity.isPresent()) {
            ApplicationEntity applicationEntity = optionalApplicationEntity.get();

            // accepted
            applicationEntity.setStatus(1);
            applicationRepository.save(applicationEntity);

        }
    }

    public void rejectApplicationById(String id) {
        Optional<ApplicationEntity> optionalApplicationEntity = applicationRepository.findById(id);
        if (optionalApplicationEntity.isPresent()) {
            ApplicationEntity applicationEntity = optionalApplicationEntity.get();

            // rejected
            applicationEntity.setStatus(2);
            applicationRepository.save(applicationEntity);

        }
    }

    public void deleteApplicationById(String id) {
        applicationRepository.deleteById(id);
    }


    public List<ApplicationEntity> getApplications() {
        return applicationRepository.findAll();
    }
}
