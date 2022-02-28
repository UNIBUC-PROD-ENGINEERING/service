package ro.unibuc.hello.controller;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import com.github.cliftonlabs.json_simple.Jsoner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.hello.data.TaskEntity;
import ro.unibuc.hello.data.TaskRepository;
import ro.unibuc.hello.dto.Greeting;

@Controller
public class HelloWorldController {

    @Autowired
    private TaskRepository taskRepository;

    private static final String helloTemplate = "Hello, %s!";
    private static final String informationTemplate = "%s : %s!";
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/hello-world")
    @ResponseBody
    public Greeting sayHello(@RequestParam(name="name", required=false, defaultValue="Stranger") String name) {
        return new Greeting(counter.incrementAndGet(), String.format(helloTemplate, name));
    }

    @GetMapping("/tasks")
    @ResponseBody
    public String listAll() {

        List<TaskEntity> entityList = taskRepository.findAll();
        System.out.println(entityList.get(0).id);
        String json = Jsoner.serialize(entityList);
        return json;
    }

    @GetMapping("/task")
    @ResponseBody
    public String listById(String id) {

        TaskEntity entity = taskRepository.findById(id).orElse(null);
        if (entity == null) {
            return "{ Error: This task was not found}";
        }
        else
        {
            String json = Jsoner.serialize(entity);
            return json;
        }
    }

    @PostMapping("/task")
    @ResponseBody
    public String addTask(String title, String importance) {
        taskRepository.save(new TaskEntity(title, importance));
        TaskEntity entity = taskRepository.findByTitle(title);
        String json = Jsoner.serialize(entity);
        return json;
    }

    @PostMapping("/task")
    @ResponseBody
    public String endTask(String id) {
        TaskEntity entity = taskRepository.findById(id).orElse(null);
        if (entity == null) {
            return "{ Error: This task was not found}";
        }
        else
        {
            entity.isDone = true;
            return Jsoner.serialize(entity);
        }
    }

}
