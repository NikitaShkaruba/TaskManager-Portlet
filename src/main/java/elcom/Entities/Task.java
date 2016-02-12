package elcom.Entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="task")
@NamedQueries({
        @NamedQuery(name = "select all tasks",
                    query = "select t from Task t"),

        @NamedQuery(name = "select tasks by status",
                    query = "select t from Task t where t.status = :status"),

        @NamedQuery(name = "select tasks by employee",
                    query = "select t from Task t where t.executor = :employee"),

        @NamedQuery(name = "select tasks by employee and status",
                    query = "select t from Task t where t.executor = :employee and t.status = :status")
})
public class Task implements Serializable, Cloneable {
    private long id;
    private String description;
    private Date startDate;
    private Date finishDate;
    private Employee executor;
    private Status status;
    private Priority priority;
    private String group; // There's only 1 group yet

    public Task() {}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() {
        return id;
    }
    @Basic
    @Column(name = "name")
    public String getDescription() {
        return description;
    }
    @OneToOne
    @JoinColumn(name="status_id")
    public Status getStatus() {
        return status;
    }
    @Transient
    public String getGroup() {
        return "Все";
    }
    @OneToOne
    @JoinColumn(name="performer_id")
    public Employee getExecutor() {
        return executor;
    }
    @OneToOne
    @JoinColumn(name="priority_id")
    public Priority getPriority() {
        return priority;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "beg_date")
    public Date getStartDate() {
        return startDate;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end_date")
    public Date getFinishDate() {
        return finishDate;
    }

    public void setId(long id) {
        this.id = id;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setStatus(Status status) {
        this.status = status;
    }
    public void setGroup(String group) {
        this.group = group;
    }
    public void setExecutor(Employee executor) {
        this.executor = executor;
    }
    public void setPriority(Priority priority) {
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
        int hash = (int)id * 51 / 17 + 322;
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
        return getClass().getCanonicalName()
                .concat("[id = ").concat(id + "").concat(", desc = ").concat(description != null ? description : "null").concat("]");
    }
}
