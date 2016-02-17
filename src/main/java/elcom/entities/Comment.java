package elcom.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="descriptions")
@NamedQueries({
        @NamedQuery(name = "select from Comment with task",
                    query = "select c from Comment c where c.task = :task"),

        @NamedQuery(name = "select from Comment",
                    query = "select c from Comment c")
})
public class Comment implements Serializable {
    private long id;
    private Task task;
    private Employee author;
    private String content;
    private Date writeDate;
    private Boolean publicComment;

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
    @OneToOne
    @JoinColumn(name="user_id")
    public Employee getAuthor() {
        return author;
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

    public void setId(long id) {
        this.id = id;
    }
    public void setTask(Task task) {
        this.task = task;
    }
    public void setAuthor(Employee author) {
        this.author = author;
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

    @Override
    public int hashCode() {
        int hash = (int)id * 83 + (publicComment ? 1 : 0);
        hash += 31 * task.hashCode() + 18;
        hash += 31 * author.hashCode() + 18;
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
        if (!(this.author.equals(other.author))) return false;
        if (!(this.content.equals(other.content))) return false;
        if (!(this.publicComment.equals(other.publicComment))) return false;

        return true;
    }

    @Override
    public String toString() {
        return content != null ? content : "null";
    }
}