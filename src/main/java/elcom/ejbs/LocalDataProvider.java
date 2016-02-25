package elcom.ejbs;

import elcom.entities.*;
import elcom.jpa.DatabaseConnector;
import elcom.jpa.TasksQueryBuilder;
import javax.ejb.Local;
import javax.ejb.Singleton;
import java.util.*;

// Handles database data retrieving
@Singleton
@Local(DataProvider.class)
public class LocalDataProvider implements DataProvider {
    private final DatabaseConnector dbc = new DatabaseConnector();

    private final List<ContactPerson> contactPersonsCache;
    private final List<Employee> employeesCache;
    private final List<Group> groupsCache;
    private final List<Organisation> organisationsCache;
    private final List<Priority> prioritiesCache;
    private final List<Status> statusesCache;
    private final List<TaskTemplate> tasktemplatesCache;
    private final List<TaskType> tasktypesCache;
    private final List<Vendor> vendorsCache;

    public LocalDataProvider() {
        contactPersonsCache = dbc.getQueryResult("select c from ContactPerson c where c.person = true");
        employeesCache = dbc.getQueryResult("select e from Employee e");
        groupsCache = dbc.getQueryResult("select g from Group g");
        organisationsCache = dbc.getQueryResult("select o from Organisation o where o.organisation = true");
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
    public Contact getContactEntityByContent(String content) {
        if (content == null)
            throw new IllegalArgumentException();

        List<Contact> contacts = dbc.getQueryResult("select c from Contact c where c.content = " + content);

        return contacts.get(0);
    }
    public ContactPerson getContactPersonEntityByName(String name) {
        if (name == null)
            return null;

        for (ContactPerson cp : contactPersonsCache)
            if (name.equals(cp.getName()))
                return cp;

        return null;
    }
    public Organisation getOrganisationEntityByName(String name) {
        if (name == null)
            return null;

        for (Organisation o : organisationsCache)
            if (name.equals(o.getName()))
                return o;

        return null;
    }
    public Employee getEmployeeEntityByName(String name) {
        if (name == null)
            throw new IllegalArgumentException();

        for (Employee e : employeesCache)
            if (name.equals(e.getName()))
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
    public List<ContactPerson> getAllContactPersons() {
        return contactPersonsCache;
    }
    public List<Organisation> getAllOrganisations() {
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

    public int countAllTasks() {
        return dbc.getTasksCountResult(new TasksQueryBuilder().getQuery());
    }
    public int countUserTasks(Employee user) {
        return dbc.getTasksCountResult(new TasksQueryBuilder().setExecutor(user).getQuery());
    }
    public int countWatchedTasks(Employee user) {
        return -322;
    }
    public int countModifiedTasks(Employee user) {
        return -265;
    }
    public int countFreeTasks() {
        List<Task> tasks = dbc.getTasksQueryResult(new TasksQueryBuilder().setStatus(getStatusEntityByName("открыта")).getQuery());

        int counter = 0;

        for (Task t : tasks)
            if (t.getExecutor() == null)
                counter += 1;

        return counter;
        }
    public int countClosedTasks() {
        return dbc.getTasksCountResult(new TasksQueryBuilder().setStatus(getStatusEntityByName("закрыта")).getQuery());
    }
    public int countContractTasks() {
        return dbc.getTasksCountResult(new TasksQueryBuilder().setType(getTasktypeEntityByName("договор")).getQuery());
    }
}
