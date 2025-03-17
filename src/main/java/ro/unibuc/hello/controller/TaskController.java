package ro.unibuc.hello.controller;

import org.springframework.http.ResponseEntity;
import ro.unibuc.hello.data.TaskEntity;

import ro.unibuc.hello.service.TaskService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // GET: Retrieve all tasks
    @GetMapping
    public List<TaskEntity> getAllTasks() {
        return taskService.getAllTasks();
    }

    // GET: Retrieve a single task by ID
    @GetMapping("/{id}")
    public TaskEntity getTaskById(@PathVariable String id) {
        return taskService.getTaskById(id);
    }

    // GET: Retrieve tasks for a party
    @GetMapping("/party/{partyId}")
    public List<TaskEntity> getTasksForParty(@PathVariable String partyId) {
        return taskService.getTasksForParty(partyId);
    }

    // GET: Retrieve tasks assigned to a user
    @GetMapping("/user/{userId}")
    public List<TaskEntity> getTasksForUser(@PathVariable String userId) {
        return taskService.getTasksForUser(userId);
    }

    // POST: Create a new task
    @PostMapping
    public TaskEntity createTask(@RequestBody TaskEntity task) {
        return taskService.createTask(task);
    }

    // PUT: Update an existing task
    @PutMapping("/{id}")
    public ResponseEntity<TaskEntity> updateTask(@PathVariable String id, @RequestBody TaskEntity updatedTask) {
        TaskEntity updated = taskService.updateTask(id, updatedTask);
        
        if (updated != null) {
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.notFound().build();
    }

    // DELETE: Remove a task
    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable String id) {
        taskService.deleteTask(id);
    }
}
