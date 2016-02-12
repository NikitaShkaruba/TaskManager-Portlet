package elcom.Entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="wfgroup")
@NamedQuery(name="select all groups", query="select g from Group g")
public class Group implements Serializable {
    private long id;
    private String name;
    private String fullName;
    private Boolean closed;

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
    @Column(name="fullname")
    public String getFullName() {
        return fullName;
    }
    @Basic
    @Column(name="closed")
    public Boolean getClosed() {
        return closed;
    }

    public void setId(long id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public void setClosed(Boolean closed) {
        this.closed = closed;
    }

    @Override
    public int hashCode() {
        int hash = (int) id;
        hash += name.hashCode();
        hash += fullName.hashCode();

        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Group))
            return false;

        Group other = (Group) obj;

        if (this.id != other.id) return false;
        if (!(this.name.equals(other.name))) return false;
        if (!(this.fullName.equals(other.fullName))) return false;

        return true;
    }

    @Override
    public String toString() {
        return fullName;
    }
}
