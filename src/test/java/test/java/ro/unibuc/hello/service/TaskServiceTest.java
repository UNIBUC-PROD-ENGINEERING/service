package test.java.ro.unibuc.hello.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import ro.unibuc.hello.data.TaskEntity;
import ro.unibuc.hello.data.UserEntity;
import ro.unibuc.hello.data.PartyEntity;
import ro.unibuc.hello.repositories.TaskRepository;
import ro.unibuc.hello.repositories.UserRepository;
import ro.unibuc.hello.repositories.PartyRepository;
import ro.unibuc.hello.service.TaskService;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PartyRepository partyRepository;

    @InjectMocks
    private TaskService taskService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void test_getAllTasks() {
        // Arrange
        List<TaskEntity> tasks = Arrays.asList(
            new TaskEntity("Task 1", "Description 1", 10, "party1", "user1"),
            new TaskEntity("Task 2", "Description 2", 20, "party1", "user2")
        );
        when(taskRepository.findAll()).thenReturn(tasks);

        // Act
        List<TaskEntity> result = taskService.getAllTasks();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Task 1", result.get(0).getName());
        assertEquals("Task 2", result.get(1).getName());
    }

    @Test
    void testCreateTask() {
        TaskEntity task = new TaskEntity("Task Name", "Task Description", 10, "partyId123", "userId123");

        assertEquals("Task Name", task.getName());
        assertEquals("Task Description", task.getDescription());
        assertEquals(10, task.getPoints());
        assertEquals("partyId123", task.getPartyId());
        assertEquals("userId123", task.getAssignedUserId());
    }

    @Test
    void test_updateTask() {
        String id = "1";
        TaskEntity existingTask = new TaskEntity("Old Task", "Old Description", 10, "party1", "user1");
        TaskEntity updatedTask = new TaskEntity("Updated Task", "Updated Description", 20, "party1", "user1");

        existingTask.setId(id);
        updatedTask.setId(id);

        when(taskRepository.findById(id)).thenReturn(Optional.of(existingTask));
        when(taskRepository.save(any(TaskEntity.class))).thenReturn(updatedTask);

        TaskEntity result = taskService.updateTask(id, updatedTask);

        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals("Updated Task", result.getName());
        assertEquals(20, result.getPoints());
    }

    @Test
    void test_updateTask_UserPointsUpdated() {
        String id = "1";
        TaskEntity existingTask = new TaskEntity("Task", "Description", 10, "party1", "user1");
        existingTask.setId(id);
        existingTask.setCompleted(true);

        UserEntity user = new UserEntity("user1", "user1@example.com", "password123");
        user.setPoints(100);

        PartyEntity party = new PartyEntity("party1", "2025-03-18");
        party.setPartyPoints(50);

        when(taskRepository.findById(id)).thenReturn(Optional.of(existingTask));
        when(userRepository.findById("user1")).thenReturn(Optional.of(user));
        when(partyRepository.findById("party1")).thenReturn(Optional.of(party));
        when(taskRepository.save(any(TaskEntity.class))).thenReturn(existingTask);
        when(userRepository.save(any(UserEntity.class))).thenReturn(user);
        when(partyRepository.save(any(PartyEntity.class))).thenReturn(party);

        TaskEntity result = taskService.updateTask(id, existingTask);

        assertNotNull(result);
        assertTrue(result.isCompleted());
        assertEquals(110, user.getPoints());
        assertEquals(60, party.getPartyPoints());
    }

    @Test
    void test_deleteTask() {
        String id = "1";
        TaskEntity task = new TaskEntity("Task to delete", "Description", 10, "party1", "user1");
        task.setId(id);

        when(taskRepository.findById(id)).thenReturn(Optional.of(task));
        doNothing().when(taskRepository).deleteById(id);

        taskService.deleteTask(id);

        verify(taskRepository, times(1)).deleteById(id);
    }

    @Test
    void test_getTaskById() {
        String id = "1";
        TaskEntity task = new TaskEntity("Task 1", "Description 1", 10, "party1", "user1");
        task.setId(id);

        when(taskRepository.findById(id)).thenReturn(Optional.of(task));

        TaskEntity result = taskService.getTaskById(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals("Task 1", result.getName());
    }

    @Test
    void test_getTasksForParty() {
        String partyId = "party1";
        List<TaskEntity> tasks = Arrays.asList(
            new TaskEntity("Task 1", "Description 1", 10, partyId, "user1"),
            new TaskEntity("Task 2", "Description 2", 15, partyId, "user2")
        );

        when(taskRepository.findByPartyId(partyId)).thenReturn(tasks);

        List<TaskEntity> result = taskService.getTasksForParty(partyId);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Task 1", result.get(0).getName());
        assertEquals("Task 2", result.get(1).getName());
    }

    @Test
    void test_getTasksForParty_NoTasks() {
        String partyId = "party2";
        when(taskRepository.findByPartyId(partyId)).thenReturn(Arrays.asList());

        List<TaskEntity> result = taskService.getTasksForParty(partyId);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void test_getTasksForUser() {
        String userId = "user1";
        List<TaskEntity> tasks = Arrays.asList(
            new TaskEntity("Task 1", "Description 1", 10, "party1", userId),
            new TaskEntity("Task 2", "Description 2", 15, "party2", userId)
        );

        when(taskRepository.findByAssignedUserId(userId)).thenReturn(tasks);

        List<TaskEntity> result = taskService.getTasksForUser(userId);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Task 1", result.get(0).getName());
        assertEquals("Task 2", result.get(1).getName());
    }

    @Test
    void test_getTasksForUser_NoTasks() {
        String userId = "user3";
        when(taskRepository.findByAssignedUserId(userId)).thenReturn(Arrays.asList());

        List<TaskEntity> result = taskService.getTasksForUser(userId);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void test_createTask_WithExistingId() {
        TaskEntity task = new TaskEntity("Task 1", "Description", 10, "party1", "user1");
        task.setId("existingId");

        when(taskRepository.existsById("existingId")).thenReturn(true);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> taskService.createTask(task));
        assertEquals("Task with this ID already exists", exception.getMessage());
    }

    @Test
    void test_getAllTasks_EmptyDatabase() {
        when(taskRepository.findAll()).thenReturn(Arrays.asList());

        List<TaskEntity> result = taskService.getAllTasks();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }


}