package ro.unibuc.hello.dto;

import ro.unibuc.hello.data.TaskEntity;

import java.util.Date;

public class TaskDTO {
    private final long id;
    private final String idTask;
    private final Date dueDate;
    private final String title;
    private final Boolean isDone;
    private final String importance;

    public TaskDTO(long id, String idTask, Date dueDate, String title, Boolean isDone, String importance) {
        this.id = id;
        this.dueDate = dueDate;
        this.title = title;
        this.isDone = isDone;
        this.importance = importance;
        this.idTask = idTask;
    }

    public TaskDTO(long id, TaskEntity taskEntity) {
        this.id = id;
        this.idTask = taskEntity.id;
        this.dueDate = taskEntity.dueDate;
        this.title = taskEntity.title;
        this.isDone = taskEntity.isDone;
        this.importance = taskEntity.importance;
    }

    public long getId() {
        return id;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public String getTitle() {
        return title;
    }

    public Boolean getDone() {
        return isDone;
    }

    public String getImportance() {
        return importance;
    }

    public String getIdTask() {
        return idTask;
    }
}
