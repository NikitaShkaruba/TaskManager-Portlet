package elcom.ejbs;

import elcom.Entities.*;

import javax.ejb.Local;
import java.util.List;

@Local
public interface IDatabaseConnectorLocal {
    // Methods used to get full entity instance (id-name-etc) having just name.
    Status findStatusByName(String name);
    Employee findEmployeeByName(String name);
    Priority findPriorityByName(String name);
    Task findTaskById(int id);
    Description findDescriptionByContent(String content);

    // CREATE Methods
    boolean tryCreateTask(Task task);
    boolean tryCreateDescription(Description desc);

    // READ Methods
    List<String> readEmployees();
    List<String> readPriorities();
    List<String> readStatuses();
    List<String> readGroups();
    List<Description> readDescriptions(Task task);
    List<Task> readTasks(String statusFilter, String employeeFilter);

    // UPDATE Methods
    boolean tryUpdateTask(Task task);
    boolean tryUpdateDescription(Description desc);

    // DELETE Methods
    boolean tryDeleteTask(Task task);
    boolean tryDeleteDescription(Description desc);
}
