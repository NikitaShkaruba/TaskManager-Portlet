package elcom.ejbs;

import elcom.Entities.Employee;
import elcom.Entities.Priority;
import elcom.Entities.Status;
import elcom.Entities.Task;
import javax.ejb.Local;
import java.util.List;

@Local
public interface IDatabaseConnectorLocal {
    // Methods used to get full entity instance (id-name-etc) having just name.
    Status findStatusByName(String name);
    Employee findEmployeeByName(String name);
    Priority findPriorityByName(String name);
    Task findTaskById(int id);
    // CREATE Methods
    Boolean tryCreateTask(Task task);

    // READ Methods
    List<String> readEmployees();
    List<String> readPriorities();
    List<String> readStatuses();
    List<String> readGroups();
    List<Task> readTasks(String statusFilter, String employeeFilter);

    // UPDATE Methods
    Boolean tryUpdateTask(Task task);

    // DELETE Methodjj
    Boolean tryDeleteTask(Task task);
}
