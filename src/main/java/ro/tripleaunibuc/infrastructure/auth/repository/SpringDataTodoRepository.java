package ro.tripleaunibuc.infrastructure.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.tripleaunibuc.domain.auth.model.Todo;

import java.util.List;
import java.util.Optional;

public interface SpringDataTodoRepository extends JpaRepository<Todo, Integer> {

    List<Todo> findByUser(String user);

    Optional<Todo> findByIdAndUser(Integer id, String user);
}
