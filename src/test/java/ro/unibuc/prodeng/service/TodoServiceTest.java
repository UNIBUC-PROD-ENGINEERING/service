package ro.unibuc.prodeng.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ro.unibuc.prodeng.exception.EntityNotFoundException;
import ro.unibuc.prodeng.model.TodoEntity;
import ro.unibuc.prodeng.model.UserEntity;
import ro.unibuc.prodeng.repository.TodoRepository;
import ro.unibuc.prodeng.request.AssignTodoRequest;
import ro.unibuc.prodeng.request.CreateTodoRequest;
import ro.unibuc.prodeng.request.EditTodoRequest;
import ro.unibuc.prodeng.response.TodoResponse;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class TodoServiceTest {

    @Mock
    private TodoRepository todoRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private TodoService todoService;

    private final UserEntity alice = new UserEntity("user-1", "Alice", "alice@example.com");
    private final UserEntity bob = new UserEntity("user-2", "Bob", "bob@example.com");

    private final TodoEntity todo = new TodoEntity(
            "todo-1",
            "Lab",
            "Unit testing",
            "Write tests",
            "2026-06-30",
            false,
            "user-1"
    );

    @Test
    void testGetTodosByUserEmail_withTodos_returnsTodoList() throws EntityNotFoundException {
        when(userService.getUserEntityByEmail("alice@example.com")).thenReturn(alice);
        when(todoRepository.findByAssignedUserId("user-1")).thenReturn(List.of(todo));

        List<TodoResponse> result = todoService.getTodosByUserEmail("alice@example.com");

        assertEquals(1, result.size());
        assertEquals("todo-1", result.get(0).id());
        assertEquals("Lab", result.get(0).subject());
        assertEquals("Unit testing", result.get(0).taskName());
        assertEquals("Write tests", result.get(0).description());
        assertEquals("2026-06-30", result.get(0).deadline());
        assertFalse(result.get(0).done());
        assertEquals("Alice", result.get(0).assigneeName());
        assertEquals("alice@example.com", result.get(0).assigneeEmail());

        verify(userService).getUserEntityByEmail("alice@example.com");
        verify(todoRepository).findByAssignedUserId("user-1");
    }

    @Test
    void testGetTodosByUserEmail_withNoTodos_returnsEmptyList() throws EntityNotFoundException {
        when(userService.getUserEntityByEmail("alice@example.com")).thenReturn(alice);
        when(todoRepository.findByAssignedUserId("user-1")).thenReturn(List.of());

        List<TodoResponse> result = todoService.getTodosByUserEmail("alice@example.com");

        assertTrue(result.isEmpty());
    }

    @Test
    void testGetTodoById_existingTodo_returnsTodo() throws EntityNotFoundException {
        when(todoRepository.findById("todo-1")).thenReturn(Optional.of(todo));
        when(userService.getUserEntityById("user-1")).thenReturn(alice);

        TodoResponse result = todoService.getTodoById("todo-1");

        assertEquals("todo-1", result.id());
        assertEquals("Lab", result.subject());
        assertEquals("Alice", result.assigneeName());
    }

    @Test
    void testGetTodoById_missingTodo_throwsEntityNotFoundException() {
        when(todoRepository.findById("missing")).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> todoService.getTodoById("missing"));

        verify(todoRepository).findById("missing");
        verifyNoInteractions(userService);
    }

    @Test
    void testCreateTodo_validRequest_savesAndReturnsTodo() throws EntityNotFoundException {
        CreateTodoRequest request = new CreateTodoRequest(
                "Lab",
                "Unit testing",
                "Write tests",
                "2026-06-30",
                "alice@example.com"
        );

        TodoEntity savedTodo = new TodoEntity(
                "todo-1",
                "Lab",
                "Unit testing",
                "Write tests",
                "2026-06-30",
                false,
                "user-1"
        );

        when(userService.getUserEntityByEmail("alice@example.com")).thenReturn(alice);
        when(todoRepository.save(any(TodoEntity.class))).thenReturn(savedTodo);

        TodoResponse result = todoService.createTodo(request);

        assertEquals("todo-1", result.id());
        assertEquals("Lab", result.subject());
        assertEquals("Unit testing", result.taskName());
        assertEquals("Write tests", result.description());
        assertFalse(result.done());
        assertEquals("Alice", result.assigneeName());

        verify(todoRepository).save(any(TodoEntity.class));
    }

    @Test
    void testSetDone_existingTodo_updatesDoneValue() throws EntityNotFoundException {
        TodoEntity updatedTodo = new TodoEntity(
                "todo-1",
                "Lab",
                "Unit testing",
                "Write tests",
                "2026-06-30",
                true,
                "user-1"
        );

        when(todoRepository.findById("todo-1")).thenReturn(Optional.of(todo));
        when(todoRepository.save(any(TodoEntity.class))).thenReturn(updatedTodo);
        when(userService.getUserEntityById("user-1")).thenReturn(alice);

        TodoResponse result = todoService.setDone("todo-1", true);

        assertTrue(result.done());
        assertEquals("todo-1", result.id());

        verify(todoRepository).save(any(TodoEntity.class));
    }

    @Test
    void testSetDone_missingTodo_throwsEntityNotFoundException() {
        when(todoRepository.findById("missing")).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> todoService.setDone("missing", true));

        verify(todoRepository, never()).save(any());
    }

    @Test
    void testAssign_existingTodo_changesAssignee() throws EntityNotFoundException {
        AssignTodoRequest request = new AssignTodoRequest("bob@example.com");

        TodoEntity assignedTodo = new TodoEntity(
                "todo-1",
                "Lab",
                "Unit testing",
                "Write tests",
                "2026-06-30",
                false,
                "user-2"
        );

        when(todoRepository.findById("todo-1")).thenReturn(Optional.of(todo));
        when(userService.getUserEntityByEmail("bob@example.com")).thenReturn(bob);
        when(todoRepository.save(any(TodoEntity.class))).thenReturn(assignedTodo);

        TodoResponse result = todoService.assign("todo-1", request);

        assertEquals("Bob", result.assigneeName());
        assertEquals("bob@example.com", result.assigneeEmail());

        verify(todoRepository).save(any(TodoEntity.class));
    }

    @Test
    void testAssign_missingTodo_throwsEntityNotFoundException() {
        AssignTodoRequest request = new AssignTodoRequest("bob@example.com");
        when(todoRepository.findById("missing")).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> todoService.assign("missing", request));

        verify(todoRepository, never()).save(any());
    }

    @Test
    void testEdit_existingTodo_updatesFields() throws EntityNotFoundException {
        EditTodoRequest request = new EditTodoRequest(
                "Updated subject",
                "Updated task",
                "Updated description",
                "2026-07-01"
        );

        TodoEntity editedTodo = new TodoEntity(
                "todo-1",
                "Updated subject",
                "Updated task",
                "Updated description",
                "2026-07-01",
                false,
                "user-1"
        );

        when(todoRepository.findById("todo-1")).thenReturn(Optional.of(todo));
        when(todoRepository.save(any(TodoEntity.class))).thenReturn(editedTodo);
        when(userService.getUserEntityById("user-1")).thenReturn(alice);

        TodoResponse result = todoService.edit("todo-1", request);

        assertEquals("Updated subject", result.subject());
        assertEquals("Updated task", result.taskName());
        assertEquals("Updated description", result.description());
        assertEquals("2026-07-01", result.deadline());

        verify(todoRepository).save(any(TodoEntity.class));
    }

    @Test
    void testEdit_missingTodo_throwsEntityNotFoundException() {
        EditTodoRequest request = new EditTodoRequest(
                "Updated subject",
                "Updated task",
                "Updated description",
                "2026-07-01"
        );

        when(todoRepository.findById("missing")).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> todoService.edit("missing", request));

        verify(todoRepository, never()).save(any());
    }

    @Test
    void testDeleteTodo_existingTodo_deletesTodo() throws EntityNotFoundException {
        when(todoRepository.existsById("todo-1")).thenReturn(true);

        todoService.deleteTodo("todo-1");

        verify(todoRepository).deleteById("todo-1");
    }

    @Test
    void testDeleteTodo_missingTodo_throwsEntityNotFoundException() {
        when(todoRepository.existsById("missing")).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> todoService.deleteTodo("missing"));

        verify(todoRepository, never()).deleteById(any());
    }
}