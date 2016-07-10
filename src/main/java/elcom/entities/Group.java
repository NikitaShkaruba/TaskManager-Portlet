package elcom.entities;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name="wfgroup")
public class Group implements Serializable {
    private long id;
    private String name;
    private String nickName;
    private Boolean closed;

    @Id
    @GeneratedValue
    public long getId() {
        return id;
    }

    @Basic
    @Column(name="fullname")
    public String getName() {
        return name;
    }
    @Basic
    @Column(name="name")
    public String getNickName() {
        return nickName;
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
    public void setNickName(String fullName) {
        this.nickName = fullName;
    }
    public void setClosed(Boolean closed) {
        this.closed = closed;
    }

    @Override
    public int hashCode() {
        int hash = (int) id;
        hash += name.hashCode();
        hash += nickName.hashCode();

        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Group))
            return false;

        Group other = (Group) obj;

        if (this.id != other.id) return false;
        if (!(this.name.equals(other.name))) return false;
        if (!(this.nickName.equals(other.nickName))) return false;
        if (!(this.closed.equals(other.closed))) return false;

        return true;
    }

    @Override
    public String toString() {
        return name;
    }
}
