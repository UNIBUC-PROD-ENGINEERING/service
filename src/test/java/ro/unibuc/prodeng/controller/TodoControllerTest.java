package ro.unibuc.prodeng.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ro.unibuc.prodeng.exception.EntityNotFoundException;
import ro.unibuc.prodeng.request.AssignTodoRequest;
import ro.unibuc.prodeng.request.CreateTodoRequest;
import ro.unibuc.prodeng.request.EditTodoRequest;
import ro.unibuc.prodeng.response.TodoResponse;
import ro.unibuc.prodeng.service.MetricsService;
import ro.unibuc.prodeng.service.TodoService;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
class TodoControllerTest {

    @Mock
    private TodoService todoService;

    @Mock
    private MetricsService metricsService;

    @InjectMocks
    private TodoController todoController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    private TodoResponse todo1;
    private TodoResponse todo2;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(todoController).build();
        objectMapper = new ObjectMapper();

        todo1 = new TodoResponse(
                "todo-1",
                "Lab",
                "Unit testing",
                "Write tests",
                "2026-06-30",
                false,
                "Alice",
                "alice@example.com"
        );

        todo2 = new TodoResponse(
                "todo-2",
                "Project",
                "Backend",
                "Finish backend",
                "2026-07-01",
                true,
                "Alice",
                "alice@example.com"
        );
    }

    @Test
    void testGetTodosByUserEmail_withTodos_returnsList() throws Exception {
        when(todoService.getTodosByUserEmail("alice@example.com"))
                .thenReturn(List.of(todo1, todo2));

        mockMvc.perform(get("/api/todos")
                        .param("assigneeEmail", "alice@example.com")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is("todo-1")))
                .andExpect(jsonPath("$[0].subject", is("Lab")))
                .andExpect(jsonPath("$[0].taskName", is("Unit testing")))
                .andExpect(jsonPath("$[0].description", is("Write tests")))
                .andExpect(jsonPath("$[0].deadline", is("2026-06-30")))
                .andExpect(jsonPath("$[0].done", is(false)))
                .andExpect(jsonPath("$[0].assigneeName", is("Alice")))
                .andExpect(jsonPath("$[0].assigneeEmail", is("alice@example.com")));

        verify(todoService).getTodosByUserEmail("alice@example.com");
    }

    @Test
    void testGetTodosByUserEmail_withNoTodos_returnsEmptyList() throws Exception {
        when(todoService.getTodosByUserEmail("alice@example.com"))
                .thenReturn(List.of());

        mockMvc.perform(get("/api/todos")
                        .param("assigneeEmail", "alice@example.com")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

        verify(todoService).getTodosByUserEmail("alice@example.com");
    }

    @Test
    void testGetTodoById_existingTodo_returnsTodo() throws Exception {
        when(todoService.getTodoById("todo-1")).thenReturn(todo1);

        mockMvc.perform(get("/api/todos/{id}", "todo-1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("todo-1")))
                .andExpect(jsonPath("$.subject", is("Lab")))
                .andExpect(jsonPath("$.taskName", is("Unit testing")))
                .andExpect(jsonPath("$.description", is("Write tests")))
                .andExpect(jsonPath("$.deadline", is("2026-06-30")))
                .andExpect(jsonPath("$.done", is(false)))
                .andExpect(jsonPath("$.assigneeName", is("Alice")))
                .andExpect(jsonPath("$.assigneeEmail", is("alice@example.com")));

        verify(todoService).getTodoById("todo-1");
    }

    @Test
    void testGetTodoById_missingTodo_returnsNotFound() throws Exception {
        when(todoService.getTodoById("missing"))
                .thenThrow(new EntityNotFoundException("missing"));

        mockMvc.perform(get("/api/todos/{id}", "missing")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(todoService).getTodoById("missing");
    }

    @Test
    void testCreateTodo_validRequest_returnsCreatedTodo() throws Exception {
        CreateTodoRequest request = new CreateTodoRequest(
                "Lab",
                "Unit testing",
                "Write tests",
                "2026-06-30",
                "alice@example.com"
        );

        when(todoService.createTodo(org.mockito.ArgumentMatchers.any(CreateTodoRequest.class))).thenReturn(todo1);

        mockMvc.perform(post("/api/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is("todo-1")))
                .andExpect(jsonPath("$.subject", is("Lab")))
                .andExpect(jsonPath("$.assigneeEmail", is("alice@example.com")));

        verify(todoService).createTodo(org.mockito.ArgumentMatchers.any(CreateTodoRequest.class));
        verify(metricsService).recordTodoCreated();
    }

    @Test
    void testSetDone_existingTodo_returnsUpdatedTodo() throws Exception {
        TodoResponse doneTodo = new TodoResponse(
                "todo-1",
                "Lab",
                "Unit testing",
                "Write tests",
                "2026-06-30",
                true,
                "Alice",
                "alice@example.com"
        );

        when(todoService.setDone("todo-1", true)).thenReturn(doneTodo);

        mockMvc.perform(patch("/api/todos/{id}/done", "todo-1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("todo-1")))
                .andExpect(jsonPath("$.done", is(true)));

        verify(todoService).setDone("todo-1", true);
        verify(metricsService).recordTodoCompleted();
    }

    @Test
    void testSetDone_missingTodo_returnsNotFound() throws Exception {
        when(todoService.setDone("missing", true))
                .thenThrow(new EntityNotFoundException("missing"));

        mockMvc.perform(patch("/api/todos/{id}/done", "missing")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("true"))
                .andExpect(status().isNotFound());

        verify(todoService).setDone("missing", true);
    }

    @Test
    void testAssign_existingTodo_returnsUpdatedAssignee() throws Exception {
        AssignTodoRequest request = new AssignTodoRequest("bob@example.com");

        TodoResponse assignedTodo = new TodoResponse(
                "todo-1",
                "Lab",
                "Unit testing",
                "Write tests",
                "2026-06-30",
                false,
                "Bob",
                "bob@example.com"
        );

        when(todoService.assign(eq("todo-1"), org.mockito.ArgumentMatchers.any(AssignTodoRequest.class)))
                .thenReturn(assignedTodo);

        mockMvc.perform(patch("/api/todos/{id}/assignee", "todo-1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("todo-1")))
                .andExpect(jsonPath("$.assigneeName", is("Bob")))
                .andExpect(jsonPath("$.assigneeEmail", is("bob@example.com")));

        verify(todoService).assign(eq("todo-1"), org.mockito.ArgumentMatchers.any(AssignTodoRequest.class));
    }

    @Test
    void testEdit_existingTodo_returnsEditedTodo() throws Exception {
        EditTodoRequest request = new EditTodoRequest(
                "Updated subject",
                "Updated task",
                "Updated description",
                "2026-07-01"
        );

        TodoResponse editedTodo = new TodoResponse(
                "todo-1",
                "Updated subject",
                "Updated task",
                "Updated description",
                "2026-07-01",
                false,
                "Alice",
                "alice@example.com"
        );

        when(todoService.edit(eq("todo-1"), org.mockito.ArgumentMatchers.any(EditTodoRequest.class)))
                .thenReturn(editedTodo);

        mockMvc.perform(patch("/api/todos/{id}/description", "todo-1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.subject", is("Updated subject")))
                .andExpect(jsonPath("$.taskName", is("Updated task")))
                .andExpect(jsonPath("$.description", is("Updated description")))
                .andExpect(jsonPath("$.deadline", is("2026-07-01")));

        verify(todoService).edit(eq("todo-1"), org.mockito.ArgumentMatchers.any(EditTodoRequest.class));
    }

    @Test
    void testDeleteTodo_existingTodo_returnsNoContent() throws Exception {
        doNothing().when(todoService).deleteTodo("todo-1");

        mockMvc.perform(delete("/api/todos/{id}", "todo-1"))
                .andExpect(status().isNoContent());

        verify(todoService).deleteTodo("todo-1");
    }

    @Test
    void testDeleteTodo_missingTodo_returnsNotFound() throws Exception {
        doThrow(new EntityNotFoundException("missing"))
                .when(todoService).deleteTodo("missing");

        mockMvc.perform(delete("/api/todos/{id}", "missing"))
                .andExpect(status().isNotFound());

        verify(todoService).deleteTodo("missing");
    }
}