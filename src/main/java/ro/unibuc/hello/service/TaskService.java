package ro.unibuc.hello.service;

import org.springframework.stereotype.Service;
import ro.unibuc.hello.data.UserEntity;
import ro.unibuc.hello.data.PartyEntity;
import ro.unibuc.hello.data.TaskEntity;
import ro.unibuc.hello.repositories.TaskRepository;
import ro.unibuc.hello.repositories.UserRepository;
import ro.unibuc.hello.repositories.PartyRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final PartyRepository partyRepository;

    public TaskService(TaskRepository taskRepository, PartyRepository partyRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.partyRepository = partyRepository;
        this.userRepository = userRepository;
    }

    public List<TaskEntity> getAllTasks() {
        return taskRepository.findAll();
    }

    public TaskEntity getTaskById(String id) {
        return taskRepository.findById(id).orElse(null);
    }

    public List<TaskEntity> getTasksForParty(String partyId) {
        return taskRepository.findByPartyId(partyId);
    }

    public List<TaskEntity> getTasksForUser(String userId) {
        return taskRepository.findByAssignedUserId(userId);
    }

    public TaskEntity createTask(TaskEntity task) {
        return taskRepository.save(task);
    }

    public TaskEntity updateTask(String id, TaskEntity updatedTask) {
        Optional<TaskEntity> existingTask = taskRepository.findById(id);
        if (existingTask.isPresent()) {
            TaskEntity task = existingTask.get();

            // Dacă task-ul este completat
            if (updatedTask.isCompleted() && !task.isCompleted()) {
                // Obține utilizatorul atribuit
                String assignedUserId = task.getAssignedUserId();
                Optional<UserEntity> userOpt = userRepository.findById(assignedUserId);

                if (userOpt.isPresent()) {
                    UserEntity user = userOpt.get();
                    int currentPoints = user.getPoints();
                    int taskPoints = task.getPoints();

                    // Actualizează punctele utilizatorului
                    user.setPoints(currentPoints + taskPoints);
                    userRepository.save(user);  // Salvează utilizatorul cu noile puncte

                    // Actualizează punctele petrecerii
                    Optional<PartyEntity> partyOpt = partyRepository.findById(task.getPartyId());
                    if (partyOpt.isPresent()) {
                        PartyEntity party = partyOpt.get();
                        int partyPoints = party.getPartyPoints();
                        party.setPartyPoints(partyPoints + taskPoints);  // Adaugă punctele task-ului la punctele petrecerii
                        partyRepository.save(party);  // Salvează petrecerea cu noile puncte
                    }
                }
            }

            // Actualizează task-ul
            updatedTask.setId(id);
            return taskRepository.save(updatedTask);
        }
        return null;
    }
    public void deleteTask(String id) {
        taskRepository.deleteById(id);
    }
}
