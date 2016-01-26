package elcom.main;

import elcom.enums.TaskPriority;
import elcom.enums.TaskStatus;

import javax.persistence.*;
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
// TODO: 16.01.16 Make use a startDate, finishDate
    @Entity
    @Table(name="tasks")
class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    private String description;
    private String executor;
    private TaskStatus status;
    private TaskPriority priority;
    private Date startDate;
    private Date finishDate;

    public Task(){}

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

    //Subject to remove since id field is immutable outside of JPA.
    /*
    public void setId(int id) {
        this.id = id;
    }
    */
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
