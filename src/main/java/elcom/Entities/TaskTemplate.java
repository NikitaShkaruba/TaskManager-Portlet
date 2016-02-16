package elcom.Entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="tasktemplates")
@NamedQuery(name="select from TaskTemplate", query="select t from TaskTemplate t")
public class TaskTemplate implements Serializable{
    private long id;
    private String name;
    private Boolean copyName;
    private Boolean copyFinishDate;
    private Boolean copyPriority;
    private Boolean copyExecutor;
    private Boolean copyExecutorGroup;
    private Boolean copyComments;
    private Task task;

    @Id
    @GeneratedValue
    public long getId() {
        return id;
    }
    @Basic
    @Column(name="name")
    public String getName() {
        return name;
    }
    @Basic
    @Column(name="copy_name")
    public Boolean getCopyName() {
        return copyName;
    }
    @Basic
    @Column(name="copy_end_date")
    public Boolean getCopyFinishDate() {
        return copyFinishDate;
    }
    @Basic
    @Column(name="copy_priority")
    public Boolean getCopyPriority() {
        return copyPriority;
    }
    @Basic
    @Column(name="copy_performer")
    public Boolean getCopyExecutor() {
        return copyExecutor;
    }
    @Basic
    @Column(name="copy_performer_group")
    public Boolean getCopyExecutorGroup() {
        return copyExecutorGroup;
    }
    @Basic
    @Column(name="copy_descriptions")
    public Boolean getCopyComments() {
        return copyComments;
    }
    @OneToOne
    @JoinColumn(name="task_id")
    public Task getTask() {
        return task;
    }

    public void setId(long id) {
        this.id = id;
    }
    public void setName(String description) {
        this.name = description;
    }
    public void setCopyName(Boolean copyName) {
        this.copyName = copyName;
    }
    public void setCopyFinishDate(Boolean copyFinishDate) {
        this.copyFinishDate = copyFinishDate;
    }
    public void setCopyPriority(Boolean copyPriority) {
        this.copyPriority = copyPriority;
    }
    public void setCopyExecutor(Boolean copyExecutor) {
        this.copyExecutor = copyExecutor;
    }
    public void setCopyExecutorGroup(Boolean copyExecutorGroup) {
        this.copyExecutorGroup = copyExecutorGroup;
    }
    public void setCopyComments(Boolean copyComments) {
        this.copyComments = copyComments;
    }
    public void setTask(Task task) {
        this.task = task;
    }

    @Override
    public int hashCode() {
        int hash = (int) id;
        hash += name.hashCode();
        hash += copyName.hashCode();
        hash += copyFinishDate.hashCode();
        hash += copyPriority.hashCode();
        hash += copyExecutor.hashCode();
        hash += copyExecutorGroup.hashCode();
        hash += copyComments.hashCode();
        hash += task.hashCode();

        return hash;
    }
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof TaskTemplate))
            return false;

        TaskTemplate other = (TaskTemplate) obj;

        if (this.id != other.id) return false;
        if (!(this.name.equals(other.name))) return false;
        if (!(this.copyName.equals(other.copyName))) return false;
        if (!(this.copyFinishDate.equals(other.copyFinishDate))) return false;
        if (!(this.copyPriority.equals(other.copyPriority))) return false;
        if (!(this.copyExecutor.equals(other.copyExecutor))) return false;
        if (!(this.copyExecutor.equals(other.copyExecutorGroup))) return false;
        if (!(this.copyComments.equals(other.copyComments))) return false;
        if (!(this.task.equals(other.task))) return false;

        return true;
    }
    @Override
    public String toString() {
        return name;
    }
}
