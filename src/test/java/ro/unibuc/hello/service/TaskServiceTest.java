package test.java.ro.unibuc.hello.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.MockitoAnnotations;
import ro.unibuc.hello.data.PartyEntity;
import ro.unibuc.hello.data.TaskEntity;
import ro.unibuc.hello.data.UserEntity;
import ro.unibuc.hello.repositories.PartyRepository;
import ro.unibuc.hello.repositories.TaskRepository;
import ro.unibuc.hello.repositories.UserRepository;
import ro.unibuc.hello.service.TaskService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PartyRepository partyRepository;

    @InjectMocks
    private TaskService taskService;

    private TaskEntity task;

    @BeforeEach
    void setUp() {
        task = new TaskEntity("Sample Task", "Description", 10, "party1", "user1");
        task.setId("1");
    }

    @Test
    void test_getAllTasks() {
        when(taskRepository.findAll()).thenReturn(List.of(task));
        List<TaskEntity> tasks = taskService.getAllTasks();
        assertEquals(1, tasks.size());
        assertEquals("Sample Task", tasks.get(0).getName());
    }

    @Test
    void test_getTaskById() {
        when(taskRepository.findById("1")).thenReturn(Optional.of(task));
        TaskEntity foundTask = taskService.getTaskById("1");
        assertNotNull(foundTask);
        assertEquals("Sample Task", foundTask.getName());
    }

    @Test
    void test_getTasksForParty() {
        when(taskRepository.findByPartyId("party1")).thenReturn(List.of(task));
        List<TaskEntity> tasks = taskService.getTasksForParty("party1");
        assertEquals(1, tasks.size());
        assertEquals("Sample Task", tasks.get(0).getName());
    }

    @Test
    void test_getTasksForUser() {
        when(taskRepository.findByAssignedUserId("user1")).thenReturn(List.of(task));
        List<TaskEntity> tasks = taskService.getTasksForUser("user1");
        assertEquals(1, tasks.size());
        assertEquals("Sample Task", tasks.get(0).getName());
    }

    @Test
    void test_createTask() {
        when(taskRepository.save(task)).thenReturn(task);
        TaskEntity createdTask = taskService.createTask(task);
        assertNotNull(createdTask);
        assertEquals("Sample Task", createdTask.getName());
    }

    
    @Test
    void test_updateTask_UserPointsUpdated() {
        TaskEntity updatedTask = new TaskEntity("Updated Task", "Updated Description", 10, "party1", "user1");
        updatedTask.setId("1");
        updatedTask.setCompleted(true);

        UserEntity user = new UserEntity("User Name", "user@email.com", "password");
        user.setId("user1");
        user.setPoints(0);

        PartyEntity party = new PartyEntity("Party Name", "2025-12-31");
        party.setId("party1");
        party.setPartyPoints(0);

        when(taskRepository.findById("1")).thenReturn(Optional.of(task));
        when(userRepository.findById("user1")).thenReturn(Optional.of(user));
        when(partyRepository.findById("party1")).thenReturn(Optional.of(party));
        when(taskRepository.save(any(TaskEntity.class))).thenReturn(updatedTask);

        TaskEntity result = taskService.updateTask("1", updatedTask);

        assertNotNull(result);
        assertTrue(result.isCompleted());
        assertEquals(10, user.getPoints());
        assertEquals(10, party.getPartyPoints());
    }

    @Test
    void test_updateTaskWithNonexistentTask() {
        TaskEntity updatedTask = new TaskEntity("Updated Task", "Updated Description", 10, "party1", "user1");
        when(taskRepository.findById("1")).thenReturn(Optional.empty());
        
        // Verifică dacă se aruncă RuntimeException când task-ul nu este găsit
        assertThrows(RuntimeException.class, () -> taskService.updateTask("1", updatedTask));
    }

    @Test
    void test_deleteTask() {
        doNothing().when(taskRepository).deleteById("1");
        taskService.deleteTask("1");
        verify(taskRepository, times(1)).deleteById("1");
    }

    @Test
    void test_deleteNonexistentTask() {
        doThrow(new RuntimeException("Task not found")).when(taskRepository).deleteById("99");
        assertThrows(RuntimeException.class, () -> taskService.deleteTask("99"));
    }
}

