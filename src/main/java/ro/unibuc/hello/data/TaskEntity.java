package ro.unibuc.hello.data;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.annotation.Id;

import java.util.Date;

public class TaskEntity{
    @Id
    public String id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    public Date dueDate;
    public String title;
    public Boolean isDone = false;
    public String importance;

    public TaskEntity () {}

    public TaskEntity(String title, String importance, Date dueDate) {
        this.title = title;
        this.importance = importance;
        this.isDone = false;
        this.dueDate = dueDate;
    }
}
