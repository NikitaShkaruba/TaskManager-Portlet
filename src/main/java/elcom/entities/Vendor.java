package elcom.entities;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name="producers")
@org.hibernate.annotations.Where(clause = "vendor is not null")
public class Vendor implements Serializable {
    private long id;
    private String name;
    private Boolean vendor;

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
    @Column(name="vendor")
    public Boolean getVendor() {
        return vendor;
    }

    public void setId(long id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setVendor(Boolean vendor) {
        this.vendor = vendor;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Vendor))
            return false;

        Vendor other = (Vendor) obj;

        if (this.id != other.id) return false;
        if ((this.name.equals(other.name)))
            return true;

        return true;
    }
    @Override
    public String toString() {
        return name;
    }
    @Override
    public int hashCode() {
        int hash = (int) id;
        hash += name.hashCode();
        hash += vendor.hashCode();

        return hash;
    }
}
