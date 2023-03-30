package ro.unibuc.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.unibuc.hello.dto.UpdateProjectDto;
import ro.unibuc.hello.entity.ProjectEntity;
import ro.unibuc.hello.data.ProjectRepository;
import ro.unibuc.hello.dto.ProjectDto;
import ro.unibuc.hello.exception.ProjectNotFoundException;

import java.util.List;
import java.util.Optional;
@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public ProjectService() { }

    public ProjectEntity saveProject(ProjectDto projectDto){
        ProjectEntity projectEntity = new ProjectEntity();

        projectEntity.setName(projectDto.getName());
        projectEntity.setDescription(projectDto.getDescription());
        projectEntity.setUserId(projectDto.getUserId());

        return projectRepository.save(projectEntity);
    }

    public ProjectEntity getProjectById(String projectId) {
        Optional<ProjectEntity> project = projectRepository.findById(projectId);
        return project.get();
    }

    public List<ProjectEntity> getAllProjects() {
        return projectRepository.findAll();
    }

    public void deleteProjectById(String id) {
        projectRepository.deleteById(id);
    }

    public ProjectEntity updateProjectById(String id, UpdateProjectDto updateProjectDto){
        Optional<ProjectEntity> optionalProjectEntity = projectRepository.findById(id);
        if (optionalProjectEntity.isPresent()) {
            ProjectEntity projectEntity = optionalProjectEntity.get();
            if (updateProjectDto.getName() != null) {
                projectEntity.setName(updateProjectDto.getName());
            }
            if (updateProjectDto.getDescription() != null) {
                projectEntity.setDescription(updateProjectDto.getDescription());
            }

            return projectRepository.save(projectEntity);
        } else {
            throw new ProjectNotFoundException(id);
        }
    }
}
