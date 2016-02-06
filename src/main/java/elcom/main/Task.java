package elcom.main;

import java.util.Date;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

// TODO: 12.01.16 Make this bean an entity and map it to database
// TODO: 16.01.16 Make use of startDate, finishDate
// TODO: 05.02.2016 Annotate remaining fields. Look for other required annotations

@Entity
@Table(name="task")
class Task implements Serializable, Cloneable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "name", length = 100)
    private String description;
    private String status;
    private String group;
    private String executor;
    private String priority;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "beg_date")
    private Date startDate;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end_date")
    private Date finishDate;

    public Task() {}
    public Task(int id, String description, String executor, String status, String priority) {
        this.id = id;
        this.description = description;
        this.status = status;
        this.executor = executor;
        this.priority = priority;

        // TODO: 05.02.16 Work it out
        this.group = "None";
        this.startDate = new Date();
        this.finishDate = new Date();
    }
    public Task(int id, String description, String status, String group, String executor, String priority, Date startDate, Date finishDate) {
        this.id = id;
        this.description = description;
        this.status = status;
        this.group = group;
        this.executor = executor;
        this.priority = priority;
        this.startDate = startDate;
        this.finishDate = finishDate;
    }

    public int getId() {
        return id;
    }
    public String getDescription() {
        return description;
    }
    public String getStatus() {
        return status;
    }
    public String getGroup() {
        return group;
    }
    public String getExecutor() {
        return executor;
    }
    public String getPriority() {
        return priority;
    }
    public Date getStartDate() {
        return this.startDate;
    }
    public Date getFinishDate() {
        return this.finishDate;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public void setGroup(String group) {
        this.group = group;
    }
    public void setExecutor(String executorName) {
        this.executor = executorName;
    }
    public void setPriority(String priority) {
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
        if (!(object instanceof Task))
            return false;

        Task other = (Task) object;
        return (this.id == other.id);
    }
    @Override
    public String toString() {
        return getClass().getCanonicalName().concat("[id = ").concat(id + "").concat(", desc = ").concat(description != null ? description : "null").concat("]");
    }
}
