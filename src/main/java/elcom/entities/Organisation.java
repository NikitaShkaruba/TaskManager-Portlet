package elcom.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="contact")
public class Organisation {
    private long id;
    private String name;
    private List<Contact> contacts;
    private Boolean organisation;

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
    @Column(name="organization")
    public Boolean getOrganisation() {
        return organisation;
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
    public void setOrganisation(Boolean organisation) {
        this.organisation = organisation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Organisation)) return false;

        Organisation that = (Organisation) o;

        if (id != that.id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return contacts != null ? contacts.equals(that.contacts) : that.contacts == null;

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
