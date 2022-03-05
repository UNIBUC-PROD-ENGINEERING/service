package ro.unibuc.hello.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.hello.data.TaskEntity;
import ro.unibuc.hello.data.TaskRepository;
import ro.unibuc.hello.dto.TaskDTO;

@Controller
public class HelloWorldController {

    @Autowired
    private TaskRepository taskRepository;

    private static final String datePattern = "yyyy-MM-dd";
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat(datePattern);

    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/tasks")
    @ResponseBody
    public ResponseEntity<List<TaskDTO>> listAll(@RequestParam(required = false, name = "search-by") String search,
                                                 @RequestParam(required = false, name = "value") String value) {
        List<TaskDTO> entityList;
        if (search == null) {
            entityList = taskRepository.findAll().stream().map(taskEntity -> new TaskDTO(counter.incrementAndGet(), taskEntity)).
                    collect(Collectors.toList());
            return new ResponseEntity<>(entityList, HttpStatus.OK);
        }

        switch (search)
        {
            case "importance":
                entityList = taskRepository.findByImportance(value).stream().map(taskEntity -> new TaskDTO(counter.incrementAndGet(), taskEntity)).
                        collect(Collectors.toList());
                return new ResponseEntity<>(entityList, HttpStatus.OK);

            case "date":
                Date tmpDate;
                try {
                    tmpDate = dateFormat.parse(value);
                } catch (ParseException e) {
                    e.printStackTrace();
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }

                entityList = taskRepository.findByDueDate(tmpDate).stream().map(taskEntity -> new TaskDTO(counter.incrementAndGet(), taskEntity)).
                        collect(Collectors.toList());
                return new ResponseEntity<>(entityList, HttpStatus.OK);
            case "isDone":
                entityList = taskRepository.findByIsDone(Boolean.parseBoolean(value)).stream().map(taskEntity -> new TaskDTO(counter.incrementAndGet(), taskEntity)).
                        collect(Collectors.toList());
                return new ResponseEntity<>(entityList, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/task")
    @ResponseBody
    public ResponseEntity<TaskDTO> showById(String id) {

        TaskEntity entity = taskRepository.findById(id).orElse(null);
        if (entity == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else
        {
            return new ResponseEntity<>(new TaskDTO(counter.incrementAndGet(), entity), HttpStatus.OK);
        }
    }

    @PostMapping("/task")
    @ResponseBody
    public ResponseEntity<TaskDTO> addTask(@RequestBody TaskEntity taskEntity) {
        taskEntity.isDone = false;
        taskRepository.save(taskEntity);
        TaskDTO taskDTO = new TaskDTO(counter.incrementAndGet(), taskEntity);
        return new ResponseEntity<>(taskDTO, HttpStatus.CREATED);
    }

    @PutMapping("/task")
    @ResponseBody
    public ResponseEntity<TaskDTO> endTask(String id) {
        TaskEntity entity = taskRepository.findById(id).orElse(null);
        if (entity == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else
        {
            entity.isDone = true;
            taskRepository.save(entity);
            return new ResponseEntity<>(new TaskDTO(counter.incrementAndGet(), entity), HttpStatus.ACCEPTED);
        }
    }

}
