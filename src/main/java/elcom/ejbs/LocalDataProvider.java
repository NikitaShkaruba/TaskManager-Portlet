package elcom.ejbs;

import elcom.entities.*;
import elcom.jpa.DatabaseConnector;
import elcom.jpa.TasksQueryBuilder;
import javax.ejb.Local;
import javax.ejb.Singleton;
import java.util.*;
import java.util.function.Predicate;

// Handles database data retrieving
@Singleton
@Local(DataProvider.class)
public class LocalDataProvider implements DataProvider {
    private final DatabaseConnector dbc = new DatabaseConnector();

    private final List<Employee> employeesCache;
    private final List<Group> groupsCache;
    private final List<Priority> prioritiesCache;
    private final List<Status> statusesCache;
    private final List<TaskTemplate> tasktemplatesCache;
    private final List<TaskType> tasktypesCache;
    private final List<Vendor> vendorsCache;

    public LocalDataProvider() {
        employeesCache = dbc.getQueryResult("select e from Employee e where e.active = true");
        groupsCache = dbc.getQueryResult("select g from Group g");
        prioritiesCache = dbc.getQueryResult("select p from Priority p");
        statusesCache = dbc.getQueryResult("select s from Status s");
        tasktemplatesCache = dbc.getQueryResult("select t from TaskTemplate t");
        tasktypesCache = dbc.getQueryResult("select t from TaskType t");
        vendorsCache = dbc.getQueryResult("select v from Vendor v");
    }

    private boolean tryCopyComments(Task from, Task to) {
        List<Comment> comments = getTaskComments(from);
        for (Comment c : comments) {
            c.setTask(to);
            c.setId(0);
            try { persist(c);}
            catch (Exception e) {e.printStackTrace(); return false;}
        }

        return true;
    }

    public Task instantiateTaskByTemplate(TaskTemplate tt) {
        Task newborn = new Task();

        if (tt.getCopyName())
            newborn.setDescription(tt.getTask().getDescription());
        if (tt.getCopyFinishDate())
            newborn.setFinishDate(tt.getTask().getFinishDate());
        if (tt.getCopyPriority())
            newborn.setPriority(tt.getTask().getPriority());
        if (tt.getCopyExecutor())
            newborn.setExecutor(tt.getTask().getExecutor());
        if (tt.getCopyExecutorGroup())
            newborn.setExecutorGroup(tt.getTask().getExecutorGroup());

        if (tt.getCopyComments())
            if (!tryCopyComments(tt.getTask(), newborn))
                return null;

        newborn.setStartDate(new Date());

        return newborn;
    }

    public void persist(Object o) {
        if (o == null)
            throw new IllegalArgumentException();

        dbc.persist(o);
    }

    public Comment getCommentEntityByContent(String content) {
        if (content == null)
            throw new IllegalArgumentException();

        List<Comment> comments = dbc.getQueryResult("select c from Comment c where c.content = " + content);

        return comments.get(0);
    }
    public Contact getContactEntityByName(String name) {
        if (name == null)
            throw new IllegalArgumentException();

        List<Contact> contacts = dbc.getQueryResult("select c from Contact c where c.content = '" + name + "'");

        return contacts.get(0);
    }
    public Employee getEmployeeEntityByName(String name) {
        if (name == null)
            throw new IllegalArgumentException();

        for (Employee e : employeesCache)
            if (name.equals(e.getName()))
                return e;
        //If no employees with given name were found, maybe 'name' is actually a nickname
        for (Employee e : employeesCache)
            if (name.equals(e.getNickName()))
                return e;

        return null;
    }
    public Group getGroupEntityByName(String name) {
        if (name == null)
            throw new IllegalArgumentException();

        for (Group g : groupsCache)
            if (name.equals(g.getName()))
                return g;

        return null;
    }
    public Priority getPriorityEntityByName(String name) {
        if (name == null)
            throw new IllegalArgumentException();

        for (Priority p : prioritiesCache)
            if (name.equals(p.getName()))
                return p;

        return null;
    }
    public Status getStatusEntityByName(String name) {
        if (name == null)
            throw new IllegalArgumentException();

        for (Status s : statusesCache)
            if (name.equals(s.getName()))
                return s;

        return null;
    }
    public Task getTaskEntityById(long id) {
        return dbc.findById(Task.class, id);
    }
    public TaskTemplate getTasktemplateEntityByName(String name) {
        if (name == null)
            throw new IllegalArgumentException();

        for (TaskTemplate tt : tasktemplatesCache)
            if (name.equals(tt.getName()))
                return tt;

        return null;
    }
    public TaskType getTasktypeEntityByName(String name) {
        if (name == null)
            throw new IllegalArgumentException();

        for (TaskType tt : tasktypesCache)
            if (name.equals(tt.getName()))
                return tt;

        return null;
    }
    public Vendor getVendorEntityByName(String name) {
        if (name == null)
            throw new IllegalArgumentException();

        for (Vendor v : vendorsCache)
            if (name.equals(v.getName()))
                return v;

        return null;
    }

    public List<Comment> getAllComments() {
        return dbc.getQueryResult("select c from Comment c");
    }
    public List<Comment> getTaskComments(Task task) {
        if (task == null)
            throw new IllegalArgumentException();

        List<Comment> comments = dbc.getQueryResult("select c from Comment c where c.task.id = " + task.getId());

        return comments;
    }
    public List<Contact> getAllContactPersons() {
        return dbc.getQueryResult("select c from Contact c where c.person = true");
    }
    public List<Contact> getAllOrganisations() {
        return dbc.getQueryResult("select c from Contact c where c.organisation = true");
    }
    public List<Employee> getAllEmployees() {
        return employeesCache;
    }
    public List<Group> getAllGroups() {
        return groupsCache;
    }
    public List<Priority> getAllPriorities() {
        return prioritiesCache;
    }
    public List<Status> getAllStatuses() {
        return statusesCache;
    }
    public List<Task> getAllTasks() {
        return getTasks(new TasksQueryBuilder().getQuery());
    }
    public List<Task> getTasks(TasksQueryBuilder.TasksQuery query) {
        List<Task> tasks = dbc.getTasksQueryResult(query);

        return dbc.getTasksQueryResult(query);
    }
    public List<TaskTemplate> getAllTasktemplates() {
        return tasktemplatesCache;
    }
    public List<TaskType> getAllTasktypes() {
        return tasktypesCache;
    }
    public List<Vendor> getAllVendors() {
        return vendorsCache;
    }

    public long countTasks() {
        return (Long)dbc.getQueryResult("count Tasks").get(0);
    }
}
