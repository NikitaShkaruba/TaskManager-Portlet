package elcom.entities;

import javax.persistence.*;

@Entity
@Table(name="wfuser")
public class wfuser {
    @Id
    @GeneratedValue
    private long id;
    @OneToOne
    @JoinColumn(name="person_id")
    public Employee employee;

    public String getName() {
        return employee.getName();
    }
    public void setName(String name) {
        employee.setName(name);
    }
}