package elcom.main;

import elcom.enums.TaskPriority;
import elcom.enums.TaskStatus;
import java.util.Date;

// TODO: 12.01.16 Make this bean an entity and map it to database
// TODO: 16.01.16 Make use of startDate, finishDate

public class Task {
    private int id;
    private String description;
    private String executor;
    private TaskStatus status;
    private TaskPriority priority;
    private Date startDate;
    private Date finishDate;

    public Task(int id, String description, String executor, TaskStatus status, TaskPriority priority) {
        this.id = id;
        this.description = description;
        this.executor = executor;
        this.status = status;
        this.priority = priority;
    }
         public Task(String description, String executor, TaskStatus status, TaskPriority priority, Date startDate, Date finishDate) {
        this.description = description;
        this.executor = executor;
        this.status = status;
        this.priority = priority;
        this.startDate = startDate;
        this.finishDate = finishDate;
    }

    public String getId() {
        return id + "";
    }
    public String getDescription() {
        return description;
    }
    public String getExecutor() {
        return executor;
    }
    public String getStatus() {
        return status.name().toLowerCase();
    }
    public Date getStartDate() {
        return this.startDate;
    }
    public Date getFinishDate() {
        return this.finishDate;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setExecutor(String executorName) {
        this.executor = executorName;
    }
    public void setStatus(TaskStatus status) {
        this.status = status;
    }
    public void setPriority(TaskPriority priority) {
        this.priority = priority;
    }
    public void setStartDate(Date date) {
        this.startDate = date;
    }
    public void setFinishDate(Date date) {
        this.finishDate = date;
    }

    public boolean equals(Task task) {
        return (this.description == task.description && this.executor == task.executor && this.status == task.status);
    }
}
