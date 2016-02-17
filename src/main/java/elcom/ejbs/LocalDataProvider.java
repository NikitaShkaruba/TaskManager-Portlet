package elcom.ejbs;

import elcom.entities.*;
import elcom.jpa.DatabaseConnector;
import elcom.jpa.QueryBuilder;

import javax.ejb.Local;
import javax.ejb.Singleton;
import java.util.*;

// Handles database data retrieving
@Singleton
@Local(DataProvider.class)
public class LocalDataProvider implements DataProvider {

    private final DatabaseConnector dbc = new DatabaseConnector();
    private final List<Group> groupsCache;
    private final List<Priority> prioritiesCache;
    private final List<Status> statusesCache;
    private final List<Vendor> vendorsCache;
    private final List<TaskType> tasktypesCache;

    public LocalDataProvider() {
        prioritiesCache = dbc.getQueryResult(new QueryBuilder(Priority.class).getQuery());
        statusesCache = dbc.getQueryResult(new QueryBuilder(Status.class).getQuery());
        groupsCache = dbc.getQueryResult(new QueryBuilder(Group.class).getQuery());
        vendorsCache = dbc.getQueryResult(new QueryBuilder(Vendor.class).addParameter("vendor", true).getQuery());
        tasktypesCache = dbc.getQueryResult(new QueryBuilder(TaskType.class).getQuery());
    }

    private List<Task> selectUbiquitousTasks(List<List<Task>> taskLists) {
        int ubiCoefficient = taskLists.size(); //guaranteed to be > 0

        Map<Task, Integer> taskCoefficients = new HashMap<>();

        for (List<Task> taskList : taskLists)
            for (Task task : taskList)
                taskCoefficients.put(task, taskCoefficients.getOrDefault(task, 0) + 1);

        List<Task> result = new ArrayList<>();
        for (Map.Entry<Task, Integer> e : taskCoefficients.entrySet())
            if (e.getValue().equals(ubiCoefficient))
                result.add(e.getKey());

        return result;
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

        List<Comment> entities = dbc.getQueryResult(new QueryBuilder(Comment.class).addParameter("content", content).getQuery());

        if (entities != null && entities.size() > 0)
            return entities.get(0);

        return null;
    }
    public Contact getOrganisationEntityByName(String name) {
        if (name == null)
            throw new IllegalArgumentException();

        List<Contact> entities = dbc.getQueryResult(new QueryBuilder(Contact.class).addParameter("name", name).getQuery());

        if (entities != null && entities.size() > 0)
            return entities.get(0);

        return null;
    }
    public Employee getEmployeeEntityByName(String name) {
        if (name == null)
            throw new IllegalArgumentException();

        List<Employee> entities = dbc.getQueryResult(new QueryBuilder(Employee.class).addParameter("name", name).getQuery());

        if (entities != null && entities.size() > 0)
            return entities.get(0);

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
        return dbc.find(Task.class, id);
    }
    public TaskTemplate getTasktemplateEntityByName(String name) {
        if (name == null)
            throw new IllegalArgumentException();

        List<TaskTemplate> entities = dbc.getQueryResult(new QueryBuilder(TaskTemplate.class).addParameter("name", name).getQuery());

        if (entities != null && entities.size() > 0)
            return entities.get(0);

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
        return dbc.getQueryResult(new QueryBuilder(Comment.class).getQuery());
    }
    public List<Comment> getTaskComments(Task task) {
        if (task == null)
            throw new IllegalArgumentException();


        return dbc.getQueryResult(new QueryBuilder(Comment.class).addParameter("task", task).getQuery());
    }
    public List<Contact> getAllOrganisations() {
        return dbc.getQueryResult(new QueryBuilder(Contact.class).addParameter("organisation", true).getQuery());
    }
    public List<Employee> getAllEmployees() {
        return dbc.getQueryResult(new QueryBuilder(Employee.class).getQuery());
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
        return dbc.getQueryResult(new QueryBuilder(Task.class).getQuery());
    }
    public List<Task> getTasks(Map<String, Object> filters) {
        if (filters == null || filters.size() == 0)
            return dbc.getQueryResult(new QueryBuilder(Task.class).getQuery());

        List<List<Task>> tasksLists = new ArrayList<>();
        for (Map.Entry<String, Object> e : filters.entrySet())
                tasksLists.add(dbc.getQueryResult(new QueryBuilder(Task.class).addParameter(e.getKey(), e.getValue()).getQuery()));

        return selectUbiquitousTasks(tasksLists);
    }
    public List<TaskTemplate> getAllTasktemplates() {
        return dbc.getQueryResult(new QueryBuilder(TaskTemplate.class).getQuery());
    }
    public List<TaskType> getAllTasktypes() {
        return tasktypesCache;
    }

    public List<Vendor> getAllVendors() {
        return vendorsCache;
    }
}
