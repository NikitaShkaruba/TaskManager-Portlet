package elcom.Entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="wfuser")
@NamedQueries({
        @NamedQuery(name = "select from Employee",
                    query = "select e from Employee e"),

        @NamedQuery(name = "select from Employee with name",
                    query = "select e from Employee e where e.name = :name")
})
public class Employee implements Serializable {
    private long id;
    private String name; //aka login aka nickname
    private String nickName;
    private Boolean active;

    public Employee(){}

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
    @Column(name="active")
    public Boolean getActive() {
        return active;
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
    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public int hashCode() {
        int hash = (int)id;
        hash += name.hashCode();
        hash += nickName.hashCode();
        hash += active.hashCode();

        return hash;
    }
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Employee))
            return false;

        Employee other = (Employee) obj;

        if (this.id != other.id) return false;
        if (!(this.name.equals(other.name))) return false;
        if (!(this.nickName.equals(other.nickName))) return false;
        if (!(this.active.equals(other.active))) return false;

        return true;
    }
    @Override
    public String toString() {
        return name;
    }
}