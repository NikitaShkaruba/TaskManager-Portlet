package elcom.entities;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name="contact")
public class Contact implements Serializable {
    private long id;
    private String content;

    @Id
    @GeneratedValue
    public long getId() {
        return id;
    }
    @Basic
    @Column(name="value")
    public String getContent() {
        return content;
    }

    public void setId(long id) {
        this.id = id;
    }
    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public int hashCode() {
        int hash = (int) id;
        hash += content.hashCode();

        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Contact))
            return false;

        Contact other = (Contact) obj;

        if (this.id != other.id) return false;
        if (!(this.content.equals(other.content))) return false;

        return true;
    }

    @Override
    public String toString() {
        return content;
    }
}
