package elcom.ejbs;

import elcom.Entities.*;
import java.util.List;

// Handles database data retrieving
public interface DatabaseConnector {
    Task instantiateTaskByTemplate(TaskTemplate tt);

    // CREATE Methods
    boolean tryCreateComment(Comment comment);
    boolean tryCreateTask(Task task);
    boolean tryCreateTaskTemplate(TaskTemplate tt);

    // READ Methods
    Comment readCommentByContent(String content);
    Employee readEmployeeByName(String name);
    Group readGroupByName(String name);
    Priority readPriorityByName(String name);
    Status readStatusByName(String name);
    Task readTaskById(long id);

    List<Comment> readAllComments();
    List<Comment> readTaskComments(Task task);
    List<Employee> readAllEmployees();
    List<Group> readAllGroups();
    List<Contact> readAllOrganisations();
    List<Priority> readAllPriorities();
    List<Status> readAllStatuses();
    List<Task> readTasks(String statusFilter, String employeeFilter);
    List<TaskTemplate> readAllTaskTemplates();
    List<Vendor> readAllVendors();

    // UPDATE Methods
    boolean tryUpdateComment(Comment comment);
    boolean tryUpdateTask(Task task);

    // DELETE Methods
    boolean tryDeleteComment(Comment comment);
    boolean tryDeleteTask(Task task);

    List<Contact> readAllOrganizations();
}
