package ro.unibuc.hello.data;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsonable;
import org.springframework.data.annotation.Id;

import java.io.IOException;
import java.io.Writer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
