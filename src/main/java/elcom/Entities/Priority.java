package elcom.Entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="priorities")
@NamedQuery(query = "select p from Priority p", name = "select all priorities")
public class Priority implements Serializable{
    private int id;
    private String name;
    private int coefficient;


    public Priority() {}


    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }
    @Basic
    @Column(name ="name")
    public String getName() {
        return name;
    }
    @Basic
    @Column(name="coef")
    public int getCoefficient() {
        return coefficient;
    }


    public void setId(int id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setCoefficient(int coefficient) {
        this.coefficient = coefficient;
    }


    @Override
    public int hashCode() {
        int hash = id * 51 / + 322;
        hash += 31 * name.hashCode();
        hash += coefficient * 19;

        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Priority))
            return false;

        Priority other = (Priority) obj;

        if (this.id != other.id)                   return false;
        if (!this.name.equals(other.name))         return false;
        if (this.coefficient != other.coefficient) return false;

        return true;
    }

    @Override
    public String toString() {
        return name;
    }
}
