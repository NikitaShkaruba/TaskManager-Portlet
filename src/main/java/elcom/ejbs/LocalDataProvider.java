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

    private final List<Comment> commentsCache;
    private final List<Contact> contactsCache;
    private final List<Contact> organisationsCache;
    private final List<Contact> contactpersonsCache;
    private final List<Employee> employeesCache;
    private final List<Group> groupsCache;
    private final List<Priority> prioritiesCache;
    private final List<Status> statusesCache;
    private final List<TaskTemplate> tasktemplatesCache;
    private final List<TaskType> tasktypesCache;
    private final List<Vendor> vendorsCache;

    public LocalDataProvider() {
        commentsCache = dbc.getNamedQueryResult("select from Comment");
        contactsCache = dbc.getNamedQueryResult("select from Contact");
        organisationsCache = filterOrganisations();
        contactpersonsCache = filterPersons();
        employeesCache = filterEmployees();
        groupsCache = dbc.getNamedQueryResult("select from Group");
        prioritiesCache = dbc.getNamedQueryResult("select from Priority");
        statusesCache = dbc.getNamedQueryResult("select from Status");
        tasktemplatesCache = dbc.getNamedQueryResult("select from TaskTemplate");
        tasktypesCache = dbc.getNamedQueryResult("select from TaskType");
        vendorsCache = dbc.getNamedQueryResult("select from Vendor");
    }

    private List<Contact> filterOrganisations() {
        List<Contact> result = new ArrayList<>();

        for (Contact c : contactsCache)
            if (c.getOrganisation() != null && c.getOrganisation())
                result.add(c);

        return result;
    }
    private List<Contact> filterPersons() {
        List<Contact> result = new ArrayList<>();

        for (Contact c : contactsCache)
            if (c.getPerson() != null && c.getPerson())
                result.add(c);

        return result;
    }
    private List<Employee> filterEmployees() {
        List<Employee> employees = dbc.getNamedQueryResult("select from Employee");

        employees.removeIf(new Predicate<Employee>() {
            @Override
            public boolean test(Employee employee) {
                return (employee.getActive() == null || employee.getActive().equals(false));
            }
        });

        return employees;
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

        for (Comment c : commentsCache)
            if (c.getContent().equals(content))
                return c;

        return null;
    }
    public Contact getContactEntityByName(String name) {
        if (name == null)
            throw new IllegalArgumentException();

        for (Contact c : contactsCache)
            if (c.getContent().equals(name))
                return c;

        return null;
    }
    public Employee getEmployeeEntityByName(String name) {
        if (name == null)
            throw new IllegalArgumentException();

        for (Employee e : employeesCache)
            if (e.getName().equals(name))
                return e;
        //If no employees with given name were found, maybe 'name' is actually a nickname
        for (Employee e : employeesCache)
            if (e.getNickName().equals(name))
                return e;

        return null;

    }
    public Group getGroupEntityByName(String name) {
        if (name == null)
            throw new IllegalArgumentException();

        for (Group g : groupsCache)
            if (g.getName().equals(name))
                return g;

        return null;
    }
    public Priority getPriorityEntityByName(String name) {
        if (name == null)
            throw new IllegalArgumentException();

        for (Priority p : prioritiesCache)
            if (p.getName().equals(name))
                return p;

        return null;
    }
    public Status getStatusEntityByName(String name) {
        if (name == null)
            throw new IllegalArgumentException();

        for (Status s : statusesCache)
            if (s.getName().equals(name))
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
            if (tt.getName().equals(name))
                return tt;

        return null;
    }
    public TaskType getTasktypeEntityByName(String name) {
        if (name == null)
            throw new IllegalArgumentException();

        for (TaskType tt : tasktypesCache)
            if (tt.getName().equals(name))
                return tt;

        return null;
    }
    public Vendor getVendorEntityByName(String name) {
        if (name == null)
            throw new IllegalArgumentException();

        for (Vendor v : vendorsCache)
            if (v.getName().equals(name))
                return v;

        return null;
    }

    public List<Comment> getAllComments() {
        return commentsCache;
    }
    public List<Comment> getTaskComments(Task task) {
        if (task == null)
            throw new IllegalArgumentException();

        List<Comment> result = new ArrayList<>();

        for (Comment c : commentsCache)
            if (c.getTask().equals(task))
                result.add(c);

        return result;
    }
    public List<Contact> getAllContactpersons() {
        return contactpersonsCache;
    }
    public List<Contact> getAllOrganisations() {
        return organisationsCache;
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
        //if we get Task with null-Status, update it with cancelled status
        for (Task t : tasks) {
            if (t.getStatus() == null) {
                t.setStatus(getStatusEntityByName("отменена"));
                persist(t);
            }
        }

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
}
