package elcom.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="contact")
public class ContactPerson {
    private long id;
    private String name;
    private List<Contact> contacts;
    private Boolean person;

    @Id
    @GeneratedValue
    public long getId() {
        return id;
    }
    @OneToMany
    @JoinTable(name = "contact_links",
            joinColumns = @JoinColumn(name="parent_contact_id"),
            inverseJoinColumns = @JoinColumn(name="child_contact_id"))
    public List<Contact> getContacts() {
        return contacts;
    }
    @Basic
    @Column(name="value")
    public String getName() {
        return name;
    }
    @Basic
    @Column(name="person")
    public Boolean getPerson() {
        return person;
    }

    public void setId(long id) {
        this.id = id;
    }
    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }
    public void setName(String content) {
        this.name = content;
    }

    public void setPerson(Boolean person) {
        this.person = person;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ContactPerson))
            return false;

        ContactPerson that = (ContactPerson) o;

        return this.id == that.id && this.name.equals(that.name);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (contacts != null ? contacts.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return name;
    }
}
