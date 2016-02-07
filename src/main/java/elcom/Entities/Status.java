package elcom.Entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="statuses")
public class Status implements Serializable {
    int id;
    String name;

    public Status(){}


    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }
    @Basic
    @Column(name="name")
    public String getName() {
        return name;
    }


    public void setId(int id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        int hash = id * 277;
        hash += name.hashCode();

        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Status))
            return false;

        Status other = (Status) obj;

        if (this.id != other.id)             return false;
        if (!(this.name.equals(other.name))) return false;

        return true;
    }

    @Override
    public String toString() {
        return this.getClass().getCanonicalName()
                .concat(": id = ").concat(id + "")
                .concat("; name = ").concat(name == null ? "null" : name);
    }
}
