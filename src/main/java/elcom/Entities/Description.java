package elcom.Entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="descriptions")
public class Description implements Serializable{
    private int id;
    private Task task;
    private Employee author;
    private Employee addressee;
    private String content;
    private Date writeDate;
    private boolean isPublicComment;

    @Id
    @GeneratedValue
    public int getId() {
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
    @OneToOne
    @JoinColumn(name="contact_id")
    public Employee getAddressee() {
        return addressee;
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
    @Column(name="public comment")
    public boolean isPublicComment() {
        return isPublicComment;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setTask(Task task) {
        this.task = task;
    }
    public void setAuthor(Employee author) {
        this.author = author;
    }
    public void setAddressee(Employee addressee) {
        this.addressee = addressee;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public void setWriteDate(Date writeDate) {
        this.writeDate = writeDate;
    }
    public void setPublicComment(boolean publicComment) {
        isPublicComment = publicComment;
    }

    @Override
    public int hashCode() {
        int hash = id * 83 + (isPublicComment ? 1 : 0);
        hash += 31 * task.hashCode() + 18;
        hash += 31 * author.hashCode() + 18;
        hash += 31 * addressee.hashCode() + 18;
        hash += 31 * content.hashCode() + 18;

        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Description))
            return false;

        Description other = (Description) obj;

        if (this.id != other.id) return false;
        if (this.task != other.task) return false;
        if (this.author != other.author) return false;
        if (this.addressee != other.addressee) return false;
        if (!(this.content.equals(other.content))) return false;
        if (this.isPublicComment != other.isPublicComment) return false;

        return true;
    }

    @Override
    public String toString() {
        return content != null ? content : "null";
    }
}
