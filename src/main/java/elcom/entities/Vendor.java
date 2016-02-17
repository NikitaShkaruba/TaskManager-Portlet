package elcom.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="producers")
@NamedQueries({
        @NamedQuery(name="select from Vendor",
                    query="select v from Vendor v"),
        @NamedQuery(name="select from Vendor with vendor",
                    query="select v from Vendor v where v.vendor = :vendor")
})
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
        if (!(this.name.equals(other.name))) return false;
        if (!(this.vendor.equals(other.vendor))) return false;

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