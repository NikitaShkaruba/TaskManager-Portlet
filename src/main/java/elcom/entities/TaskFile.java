package elcom.entities;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import javax.persistence.*;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Entity
@Table(name="taskfiles")
public class TaskFile {
    private long id;
    private Task task;
    private String name;
    private String bytes;
    private String type;
    private long size;
    private Date creationDate;

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
    @Basic
    @Column(name = "file_name")
    public String getName() {
        return name;
    }
    @Lob
    @Basic(fetch=FetchType.LAZY)
    @Column(name="file")
    public String getBytes() {
        return bytes;
    }
    @Basic
    @Column(name="file_size")
    public long getSize() {
        return size;
    }
    @Basic
    @Column(name="file_type")
    public String getType() {
        return type;
    }
    @Transient
    public StreamedContent getStream() {
        InputStream stream = new ByteArrayInputStream(bytes.getBytes());
        return new DefaultStreamedContent(stream, type, name);
    }
    @Basic
    @Column(name="created")
    public Date getCreationDate() {
        return creationDate;
    }

    public void setId(long id) {
        this.id = id;
    }
    public void setTask(Task task) {
        this.task = task;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setBytes(String bytes) {
        this.bytes = bytes;
    }
    public void setSize(long size) {
        this.size = size;
    }
    public void setType(String type) {
        this.type = type;
    }
    public void setCreationDate(Date date) {
        this.creationDate = date;
    }
    @Override
    public int hashCode() {
        int hash = (int)id * 83;
        hash += 31 * task.hashCode() + 18;
        hash += 31 * name.hashCode() + 18;
        hash += 31 * bytes.hashCode() + 18;

        return hash;
    }
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof TaskFile))
            return false;

        TaskFile other = (TaskFile) obj;

        return this.id == other.id && this.name.equals(other.name) && this.size == other.size;
    }
    @Override
    public String toString() {
        return name != null ? name : "null";
    }
}
