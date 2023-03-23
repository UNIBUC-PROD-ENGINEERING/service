package ro.unibuc.hello.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ProjectDtoTest {
    String projectId = "1";
    String projectUserId = "1";
    String projectName="TestProjectName";
    String projectDescription="This is the description";
    ProjectDto testProject= new ProjectDto(projectUserId, projectName, projectDescription);

    @Test
    void test_project_id() {
        testProject.setId(projectId);
        Assertions.assertSame(projectId, testProject.getId());
    }
    @Test
    void test_user_id() {
        Assertions.assertSame(projectUserId, testProject.getUserId());
    }
    @Test
    void test_set_user_id(){
        String randomUserId = "2";
        testProject.setUserId(randomUserId);
        Assertions.assertSame(randomUserId, testProject.getUserId());
    }
    @Test
    void test_name() {
        Assertions.assertSame(projectName, testProject.getName());
    }
    @Test
    void test_set_name() {
        String randomProjectName = "Random project name";
        testProject.setName(randomProjectName);
        Assertions.assertSame(randomProjectName, testProject.getName());
    }
    @Test
    void test_description() {
        Assertions.assertSame(projectDescription, testProject.getDescription());
    }
    @Test
    void test_set_description(){
        String randomProjectDescription = "Random project description";
        testProject.setDescription(randomProjectDescription);
        Assertions.assertSame(randomProjectDescription, testProject.getDescription());
    }
}
