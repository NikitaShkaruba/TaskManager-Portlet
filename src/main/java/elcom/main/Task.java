package elcom.main;

import elcom.enums.TaskPriority;
import elcom.enums.TaskStatus;

import java.util.Date;

/**
 * Created by Nikita Shkaruba on 12.01.16.
 * <p>
 * My Contacts:
 * Email: sh.nickita@list.ru
 * GitHub: github.com/SigmaOne
 * Vk: vk.com/wavemeaside
 */

// TODO: 12.01.16 Make this bean an entity and map it to database
// TODO: 16.01.16 Make in use startTime, finishTime
class Task {
    private int id;
    private String description;
    private String executor;
    private TaskStatus status;
    private TaskPriority prioriry;
    private Date startTime;
    private Date finishTime;

    public Task(int id, String description, String executor, TaskStatus status, TaskPriority priority) {
        this.id = id;
        this.description = description;
        this.executor = executor;
        this.status = status;
        this.prioriry = priority;
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

    public boolean equals(Task task) {
        return (this.description == task.description && this.executor == task.executor && this.status == task.status);
    }
}
