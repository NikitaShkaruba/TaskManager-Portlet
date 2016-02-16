package elcom.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="wfuser")
@NamedQuery(query = "select e from Employee e", name = "select all employees")
public class Employee implements Serializable{
    private long id;
    private String name; //aka login aka nickname
    private String fullName;
    private Boolean active;

    public Employee(){}

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
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public int hashCode() {
        int hash = (int)id;
        hash += name.hashCode();
        hash += fullName.hashCode();
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
        if (!(this.fullName.equals(other.fullName))) return false;
        if (!(this.active.equals(other.active))) return false;

        return true;
    }
    @Override
    public String toString() {
        return fullName;
    }
}