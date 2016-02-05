package elcom.main;

import elcom.enums.TaskPriority;
import elcom.enums.TaskStatus;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Nikita Shkaruba on 12.01.16.
 * <p>
 * My Contacts:
 * Email: sh.nickita@list.ru
 * GitHub: github.com/SigmaOne
 * Vk: vk.com/wavemeaside
 */

// TODO: 05.02.2016 Annotate remaining fields. Look for other required annotations
@Entity
@Table(name="task")
class Task implements Serializable, Cloneable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "name", length = 100)
    private String description;

    private String executor;

    private TaskStatus status;

    private TaskPriority priority;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "beg_date")
    private Date startDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end_date")
    private Date finishDate;

    public Task() {
    }

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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id * 51) / 17 + 322;
        hash += description.hashCode();
        hash += executor.hashCode();
        hash += status.hashCode();
        hash += priority.hashCode();
        hash += startDate.hashCode();
        hash += finishDate.hashCode();
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Task)) {
            return false;
        }
        Task other = (Task) object;
        return (this.id == other.id);
    }
    @Override
    public String toString() {
        return getClass().getCanonicalName().concat("[id = ").concat(id + "").concat(", desc = ").concat(description != null ? description : "null").concat("]");
    }

    @Override
    public Task clone() {
        try {
            return (Task) super.clone();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
