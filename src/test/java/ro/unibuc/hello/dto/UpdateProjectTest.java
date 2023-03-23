package ro.unibuc.hello.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class UpdateProjectTest {
    String name = "Project test name";
    String newName = "New project test name";
    String description = "Project test description";
    String newDescription = "New project test description";

    UpdateProjectDto updateProjectDto = new UpdateProjectDto(name, description);

    @Test
    void test_get_name() {
        Assertions.assertEquals(name, updateProjectDto.getName());
    }

    @Test
    void test_get_description() {
        Assertions.assertEquals(description, updateProjectDto.getDescription());
    }

    @Test
    void test_set_name() {
        updateProjectDto.setName(newName);
        Assertions.assertEquals(newName, updateProjectDto.getName());
    }

    @Test
    void test_set_description() {
        updateProjectDto.setDescription(newDescription);
        Assertions.assertEquals(newDescription, updateProjectDto.getDescription());
    }
}