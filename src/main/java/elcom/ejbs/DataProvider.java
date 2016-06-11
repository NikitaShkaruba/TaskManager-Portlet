package elcom.ejbs;

import elcom.entities.*;
import elcom.jpa.TasksQueryBuilder;

import java.util.List;

// Handles database data retrieving
public interface DataProvider {
    Task instantiateTaskByTemplate(TaskTemplate tt);

    void persist(Object o);

    Comment getCommentEntityByContent(String content);
    Contact getContactEntityByContent(String content);
    ContactPerson getContactPersonEntityByName(String name);
    Employee getEmployeeEntityByName(String name);
    Group getGroupEntityByName(String name);
    Organisation getOrganisationEntityByName(String name);
    Priority getPriorityEntityByName(String name);
    Status getStatusEntityByName(String name);
    Task getTaskEntityById(long id);
    TaskTemplate getTasktemplateEntityByName(String name);
    TaskType getTasktypeEntityByName(String name);
    Vendor getVendorEntityByName(String name);
    wfuser getWfuserEntityByName(String name);

    List<Comment> getAllComments();
    List<Comment> getTaskComments(Task task);
    List<TaskFile> getTaskFiles(Task task);
    List<ContactPerson> getAllContactPersons();
    List<Employee> getAllEmployees();
    List<Group> getAllGroups();
    List<Organisation> getAllOrganisations();
    List<Priority> getAllPriorities();
    List<Status> getAllStatuses();
    List<Task> getTasks(TasksQueryBuilder.TasksQuery query);
    List<Task> getAllTasks();
    List<TaskTemplate> getAllTasktemplates();
    List<TaskType> getAllTasktypes();
    List<Vendor> getAllVendors();
    List<wfuser> getAllWfusers();

    public int countAllTasks();
    public int countUserTasks(Employee user);
    public int countWatchedTasks(Employee user);
    public int countModifiedTasks(Employee user);
    public int countFreeTasks();
    public int countClosedTasks();
    public int countContractTasks();
}
