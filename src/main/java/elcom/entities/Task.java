package elcom.entities;

import org.hibernate.annotations.Formula;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="task")
@org.hibernate.annotations.Where(clause = "status_id is not null")
public class Task implements Serializable, Cloneable {
    private long id;
    private String description;
    private Contact organisation;
    private Contact contactPerson;
    private Date creationDate;
    private Date startDate;
    private Date modificationDate;
    private Date finishDate;
    private Group executorGroup;
    private Priority priority;
    private Status status;
    private Task parentTask;
    private TaskType type;
    private Boolean visible;
    private Boolean privateTask;
    private wfuser wfCreator;
    private wfuser wfExecutor;

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
    @Transient
    public Employee getCreator() {
        return wfCreator != null ? wfCreator.employee : null;
    }
    @Transient
    public Employee getExecutor() {
        return wfExecutor != null ? wfExecutor.employee : null;
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
    @OneToOne
    @JoinColumn(name="person_id")
    public Contact getContactPerson() {
        return contactPerson;
    }
    @Basic
    @Column(name="private_task")
    public Boolean getPrivateTask() {
        return privateTask;
    }
    @OneToOne
    @JoinColumn(name="owner_id")
    public wfuser getWfCreator() {
        return wfCreator;
    }
    @OneToOne
    @JoinColumn(name="performer_id")
    public wfuser getWfExecutor() {
        return wfExecutor;
    }

    //TODO: remove isCritical plug from Task entity
    @Transient
    public boolean isCritical() {
        return true;
    }

    public void setPrivateTask(Boolean privateTask) {
        this.privateTask = privateTask;
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
        this.wfCreator.employee = creator;
    }
    public void setExecutor(Employee executor) {
        this.wfExecutor.employee = executor;
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
    public void setContactPerson(Contact person) {
        this.contactPerson = person;
    }
    public void setWfCreator(wfuser wfCreator) {
        this.wfCreator = wfCreator;
    }
    public void setWfExecutor(wfuser wfExecutor) {
        this.wfExecutor = wfExecutor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task)) return false;

        Task task = (Task) o;

        if (id != task.id) return false;
        if (!description.equals(task.description)) return false;
        if (organisation != null ? !organisation.equals(task.organisation) : task.organisation != null) return false;
        if (contactPerson != null ? !contactPerson.equals(task.contactPerson) : task.contactPerson != null)
            return false;
        if (creationDate != null ? !creationDate.equals(task.creationDate) : task.creationDate != null) return false;
        if (!startDate.equals(task.startDate)) return false;
        if (modificationDate != null ? !modificationDate.equals(task.modificationDate) : task.modificationDate != null)
            return false;
        if (!finishDate.equals(task.finishDate)) return false;
        if (executorGroup != null ? !executorGroup.equals(task.executorGroup) : task.executorGroup != null)
            return false;
        if (priority != null ? !priority.equals(task.priority) : task.priority != null) return false;
        if (!status.equals(task.status)) return false;
        if (parentTask != null ? !parentTask.equals(task.parentTask) : task.parentTask != null) return false;
        if (type != null ? !type.equals(task.type) : task.type != null) return false;
        if (visible != null ? !visible.equals(task.visible) : task.visible != null) return false;
        if (privateTask != null ? !privateTask.equals(task.privateTask) : task.privateTask != null) return false;
        if (wfCreator != null ? !wfCreator.equals(task.wfCreator) : task.wfCreator != null) return false;
        return wfExecutor != null ? wfExecutor.equals(task.wfExecutor) : task.wfExecutor == null;

    }
    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + description.hashCode();
        result = 31 * result + (organisation != null ? organisation.hashCode() : 0);
        result = 31 * result + (contactPerson != null ? contactPerson.hashCode() : 0);
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        result = 31 * result + startDate.hashCode();
        result = 31 * result + (modificationDate != null ? modificationDate.hashCode() : 0);
        result = 31 * result + finishDate.hashCode();
        result = 31 * result + (executorGroup != null ? executorGroup.hashCode() : 0);
        result = 31 * result + (priority != null ? priority.hashCode() : 0);
        result = 31 * result + status.hashCode();
        result = 31 * result + (parentTask != null ? parentTask.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (visible != null ? visible.hashCode() : 0);
        result = 31 * result + (privateTask != null ? privateTask.hashCode() : 0);
        result = 31 * result + (wfCreator != null ? wfCreator.hashCode() : 0);
        result = 31 * result + (wfExecutor != null ? wfExecutor.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return getClass().getCanonicalName()
                .concat("[id = ").concat(id + "").concat(", desc = ").concat(description != null ? description : "null").concat("]");
    }
}
