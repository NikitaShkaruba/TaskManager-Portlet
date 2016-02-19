package elcom.ejbs;

import elcom.entities.*;
import elcom.jpa.TasksQueryBuilder;

import java.util.List;
import java.util.Map;

// Handles database data retrieving
public interface DataProvider {
    Task instantiateTaskByTemplate(TaskTemplate tt);

    void persist(Object o);

    Comment getCommentEntityByContent(String content);
    Contact getContactEntityByName(String name);
    Employee getEmployeeEntityByName(String name);
    Group getGroupEntityByName(String name);
    Priority getPriorityEntityByName(String name);
    Status getStatusEntityByName(String name);
    Task getTaskEntityById(long id);
    TaskTemplate getTasktemplateEntityByName(String name);
    TaskType getTasktypeEntityByName(String name);
    Vendor getVendorEntityByName(String name);

    List<Comment> getAllComments();
    List<Comment> getTaskComments(Task task);
    List<Contact> getAllOrganisations();
    List<Contact> getAllContactPersons();
    List<Employee> getAllEmployees();
    List<Group> getAllGroups();
    List<Priority> getAllPriorities();
    List<Status> getAllStatuses();
    List<Task> getTasks(TasksQueryBuilder.TasksQuery query);
    List<Task> getAllTasks();
    List<TaskTemplate> getAllTasktemplates();
    List<TaskType> getAllTasktypes();
    List<Vendor> getAllVendors();

    public long countTasks();
}
