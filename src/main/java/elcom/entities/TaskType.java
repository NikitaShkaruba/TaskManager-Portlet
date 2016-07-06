package elcom.entities;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name="tasktypes")
public class TaskType implements Serializable {
    private long id;
    private String name;

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

    public void setId(long id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof TaskType))
            return false;

        TaskType that = (TaskType) o;

        return this.id == that.id && this.name.equals(that.name);
    }
    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + name.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return name != null ? name : "null";
    }
}
