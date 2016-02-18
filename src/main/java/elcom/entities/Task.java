package elcom.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="task")
@NamedQueries({
        @NamedQuery(name = "select from Task",
                    query = "select t from Task t"),

        @NamedQuery(name = "select from Task with executor",
                query = "select t from Task t where t.executor = :executor"),

        @NamedQuery(name = "select from Task with status",
                    query = "select t from Task t where t.status = :status"),

        @NamedQuery(name = "select from Task with creator",
                    query = "select t from Task t where t.creator = :creator"),

        @NamedQuery(name = "select from Task with executorGroup",
                    query = "select t from Task t where t.executorGroup = :executorGroup"),

        @NamedQuery(name = "select from Task with priority",
                    query = "select t from Task t where t.priority = :priority")

})
public class Task implements Serializable, Cloneable {
    private long id;
    private String description;
    private Contact organisation;
    private Date creationDate;
    private Date startDate;
    private Date modificationDate;
    private Date finishDate;
    private Group executorGroup;
    private Employee creator;
    private Employee executor;
    private Priority priority;
    private Status status;
    private Task parentTask;
    private TaskType type;
    private Boolean visible;
    private boolean isPrivateTask;

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
    @JoinColumn(name="org_id")
    public Contact getOrganisation() {
        return organisation;
    }
    @OneToOne
    @JoinColumn(name="status_id")
    public Status getStatus() {
        return status;
    }
    @OneToOne
    @JoinColumn(name="gr_performer_id")
    public Group getExecutorGroup() {
        return executorGroup;
    }
    @OneToOne
    @JoinColumn(name="owner_id")
    public Employee getCreator() {
        return creator;
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
    @Column(name = "cre_date")
    public Date getCreationDate() {
        return creationDate;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "beg_date")
    public Date getStartDate() {
        return startDate;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "mod_date")
    public Date getModificationDate() {
        return modificationDate;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end_date")
    public Date getFinishDate() {
        return finishDate;
    }
    @OneToOne
    @JoinColumn(name="parent_id")
    public Task getParentTask() {
        return parentTask;
    }
    @OneToOne
    @JoinColumn(name="type_id")
    public TaskType getType() {
        return type;
    }
    @Basic
    @Column(name="visible")
    public Boolean getVisible() {
        return visible;
    }

    // TODO: 18.02.16 Bind it with db
    @Transient
    public boolean isPrivateTask() {
        return isPrivateTask;
    }
    public void setPrivateTask(boolean isPrivate) {
        this.isPrivateTask = isPrivate;
    }

    //TODO: remove isCritical plug from Task entity
    @Transient
    public boolean isCritical() {
        return true;
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
    public void setExecutorGroup(Group executorGroup) {
        this.executorGroup = executorGroup;
    }
    public void setCreator(Employee creator) {
        this.creator = creator;
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
    public void setOrganisation(Contact organisation) {
        this.organisation = organisation;
    }
    public void setModificationDate(Date modificationDate) {
        this.modificationDate = modificationDate;
    }
    public void setParentTask(Task parentTask) {
        this.parentTask = parentTask;
    }
    public void setType(TaskType type) {
        this.type = type;
    }
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    @Override
    public int hashCode() {
        int hash = (int)id * 51 / 17 + 322;
        hash += description != null ? description.hashCode() : id;
        hash += executor != null ? executor.hashCode() : id;
        hash += status != null ? status.hashCode() : id;
        hash += priority != null ? priority.hashCode() : id;
        hash += startDate != null ? startDate.hashCode() : id;
        hash += finishDate != null ? finishDate.hashCode() : id;

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
