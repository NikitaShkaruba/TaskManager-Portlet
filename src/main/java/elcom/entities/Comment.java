package elcom.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="descriptions")
public class Comment implements Serializable {
    private long id;
    private Task task;
    private String content;
    private Date writeDate;
    private Boolean publicComment;
    private wfuser wfAuthor;

    @Id
    @GeneratedValue
    public long getId() {
        return id;
    }
    @OneToOne
    @JoinColumn(name="task_id")
    public Task getTask() {
        return task;
    }
    @Transient
    public String getAuthorName() {
        return wfAuthor != null ? wfAuthor.employee.getName() : null;
    }
    @Basic
    @Column(name="name")
    public String getContent() {
        return content;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="dat")
    public Date getWriteDate() {
        return writeDate;
    }
    @Basic
    @Column(name="public_comment")
    public Boolean getPublicComment() {
        return publicComment;
    }
    @OneToOne
    @JoinColumn(name="user_id")
    public wfuser getWfAuthor() {
        return wfAuthor;
    }

    public void setId(long id) {
        this.id = id;
    }
    public void setTask(Task task) {
        this.task = task;
    }
    public void setAuthor(Employee author) {
        this.wfAuthor.employee = author;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public void setWriteDate(Date writeDate) {
        this.writeDate = writeDate;
    }
    public void setPublicComment(Boolean publicComment) {
        this.publicComment = publicComment;
    }
    public void setWfAuthor(wfuser wfAuthor) {
        this.wfAuthor = wfAuthor;
    }

    @Override
    public int hashCode() {
        int hash = (int)id * 83 + (publicComment ? 1 : 0);
        hash += 31 * task.hashCode() + 18;
        hash += 31 * wfAuthor.hashCode() + 18;
        hash += 31 * content.hashCode() + 18;

        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Comment))
            return false;

        Comment other = (Comment) obj;

        if (this.id != other.id) return false;
        if (!(this.task.equals(other.task))) return false;
        if (!(this.wfAuthor.equals(other.wfAuthor))) return false;
        if (!(this.content.equals(other.content))) return false;
        if (!(this.publicComment.equals(other.publicComment))) return false;

        return true;
    }
    @Override
    public String toString() {
        return content != null ? content : "null";
    }
}
