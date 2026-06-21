package ro.unibuc.prodeng.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import ro.unibuc.prodeng.request.AssignTodoRequest;
import ro.unibuc.prodeng.request.CreateTodoRequest;
import ro.unibuc.prodeng.request.EditTodoRequest;
import ro.unibuc.prodeng.response.TodoResponse;
import ro.unibuc.prodeng.exception.EntityNotFoundException;
import ro.unibuc.prodeng.service.TodoService;

@RestController
@RequestMapping("/api/todos")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @GetMapping
    public ResponseEntity<List<TodoResponse>> getTodosByUserEmail(@RequestParam String assigneeEmail) throws EntityNotFoundException {
        List<TodoResponse> todos = todoService.getTodosByUserEmail(assigneeEmail);
        return ResponseEntity.ok(todos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TodoResponse> getTodoById(@PathVariable String id) throws EntityNotFoundException {
        TodoResponse todo = todoService.getTodoById(id);
        return ResponseEntity.ok(todo);
    }

    @PostMapping
    public ResponseEntity<TodoResponse> createTodo(@Valid @RequestBody CreateTodoRequest request) throws EntityNotFoundException {
        TodoResponse todo = todoService.createTodo(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(todo);
    }

    @PatchMapping("/{id}/done")
    public ResponseEntity<TodoResponse> setDone(@PathVariable String id, @RequestBody boolean done) throws EntityNotFoundException {
        TodoResponse todo = todoService.setDone(id, done);
        return ResponseEntity.ok(todo);
    }

    @PatchMapping("/{id}/assignee")
    public ResponseEntity<TodoResponse> assign(@PathVariable String id, @Valid @RequestBody AssignTodoRequest request) throws EntityNotFoundException {
        TodoResponse todo = todoService.assign(id, request);
        return ResponseEntity.ok(todo);
    }

    @PatchMapping("/{id}/description")
    public ResponseEntity<TodoResponse> edit(@PathVariable String id, @Valid @RequestBody EditTodoRequest request) throws EntityNotFoundException {
        TodoResponse todo = todoService.edit(id, request);
        return ResponseEntity.ok(todo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable String id) throws EntityNotFoundException {
        todoService.deleteTodo(id);
        return ResponseEntity.noContent().build();
    }
}
