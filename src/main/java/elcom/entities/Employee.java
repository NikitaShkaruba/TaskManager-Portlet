package elcom.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="contact")
@org.hibernate.annotations.Where(clause = "id in (select w.person_id from wfuser w)")
public class Employee implements Serializable {
    private long id;
    private String name;
    private List<Contact> contacts;

    @Id
    @GeneratedValue
    public long getId() {
        return id;
    }
    @Basic
    @Column(name="value")
    public String getName() {
        return name;
    }
    @OneToMany
    @JoinTable
    public List<Contact> getContacts() {
        return contacts;
    }

    public void setId(long id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee)) return false;

        Employee employee = (Employee) o;

        if (id != employee.id) return false;
        return name.equals(employee.name);

    }
    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + name.hashCode();
        return result;
    }
    @Override
    public String toString() {
        return name;
    }
}
