package ro.unibuc.hello.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ro.unibuc.hello.data.ProjectRepository;
import ro.unibuc.hello.dto.Greeting;
import ro.unibuc.hello.dto.ProjectDto;
import ro.unibuc.hello.dto.UpdateProjectDto;
import ro.unibuc.hello.entity.ProjectEntity;

@SpringBootTest
@Tag("IT")
class UpdateProjectServiceTest {

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    ProjectService projectService;

    @Test
    void test_projectUpdateWithNameAndDescription() {
        // Arrange
        String userId = "1";
        String name = "Project test name";
        String newName = "New project test name";
        String description = "Project test description";
        String newDescription = "New project test description";

        // Act
        ProjectDto projectOld = new ProjectDto(userId, name, description);
        UpdateProjectDto projectNew = new UpdateProjectDto(newName, newDescription);

        ProjectEntity project = projectService.saveProject(projectOld);
        ProjectEntity updatedProject = projectService.updateProjectById(project.getId(), projectNew);

        // Assert
        Assertions.assertEquals(newName, updatedProject.getName());
        Assertions.assertEquals(newDescription, updatedProject.getDescription());
    }
}