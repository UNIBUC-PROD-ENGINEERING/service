package ro.unibuc.hello.data;

import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsonable;
import org.springframework.data.annotation.Id;

import java.io.IOException;
import java.io.Writer;

public class TaskEntity implements Jsonable {
    @Id
    public String id;

    public String title;
    public Boolean isDone;
    public String importance;

    public TaskEntity () {}

    public TaskEntity(String title, String importance) {
        this.title = title;
        this.importance = importance;
        this.isDone = false;
    }

    @Override
    public String toJson() {
        JsonObject json = new JsonObject();
        json.put("id", this.id);
        json.put("title", this.title);
        json.put("importance", this.importance);
        json.put("is done", this.isDone);
        return  json.toJson();
    }

    @Override
    public void toJson(Writer writable) throws IOException {
        try {
            writable.write(this.toJson());
        } catch (Exception ignored) {
        }
    }
}
