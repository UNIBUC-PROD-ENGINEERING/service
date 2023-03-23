package ro.unibuc.hello.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ApplicationTest {

    String projectId = "641cb9f23392f417094a78b5";
    String userId = "641cb9f23392f417094a78b6";
    String id = "641cb9f23392f417094a78b7";
    String randomTestId = "641cb9f23392f41709111111";
    Integer randomStatus = 1;

    // pending app
    ApplicationDto applicationDto = new ApplicationDto(projectId, userId, 0);

    @Test
    void test_id() {
        applicationDto.setId(id);
        Assertions.assertSame(id, applicationDto.getId());
    }

    @Test
    void test_get_projectId() {
        Assertions.assertEquals(projectId, applicationDto.getProjectId());
    }

    @Test
    void test_set_projectId() {
        applicationDto.setProjectId(randomTestId);
        Assertions.assertEquals(randomTestId, applicationDto.getProjectId());
    }

    @Test
    void test_get_userId() {
        Assertions.assertEquals(userId, applicationDto.getUserId());
    }

    @Test
    void test_set_userId() {
        applicationDto.setUserId(randomTestId);
        Assertions.assertEquals(randomTestId, applicationDto.getUserId());
    }

    @Test
    void test_get_status() {
        Assertions.assertSame(0, applicationDto.getStatus());
    }

    @Test
    void test_set_status() {
        applicationDto.setStatus(randomStatus);
        Assertions.assertEquals(randomStatus, applicationDto.getStatus());
    }

}