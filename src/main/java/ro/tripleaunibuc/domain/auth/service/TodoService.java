package ro.tripleaunibuc.domain.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.tripleaunibuc.domain.auth.model.Todo;
import ro.tripleaunibuc.infrastructure.auth.repository.SpringDataTodoRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final SpringDataTodoRepository springDataTodoRepository;

    public List<Todo> getTodos(String user) {
        return springDataTodoRepository.findByUser(user);
    }

    public void deleteTodo(Integer id) {
        springDataTodoRepository.deleteById(id);
    }

    public Todo getTodoByIdAndUser(Integer id, String username) {
        return springDataTodoRepository.findByIdAndUser(id, username)
                .orElseThrow(() -> new RuntimeException(String.format("Todo with id '%s' is not found", id)));
    }

    public Todo saveTodo(Todo todo) {
        return springDataTodoRepository.save(todo);
    }

}
