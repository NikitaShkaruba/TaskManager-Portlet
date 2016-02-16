package elcom.ejbs;

import elcom.Entities.*;
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

    public LocalDataProvider() {
        prioritiesCache = dbc.getQueryResult(new QueryBuilder(Priority.class).getQuery());
        statusesCache = dbc.getQueryResult(new QueryBuilder(Status.class).getQuery());
        groupsCache = dbc.getQueryResult(new QueryBuilder(Group.class).getQuery());
        vendorsCache = dbc.getQueryResult(new QueryBuilder(Vendor.class).addParameter("vendor", new Boolean(true)).getQuery());
    }

    private boolean tryCopyComments(Task from, Task to) {
        List<Comment> comments = getTaskComments(from);
        for (Comment c : comments) {
            c.setTask(to);
            c.setId(0);
            if (tryPersist(c))
                return false;
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

    public boolean tryPersist(Object o) {
        if (o == null)
            throw new IllegalArgumentException();

        try {
            dbc.persist(o);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Comment getCommentEntityByContent(String content) {
        if (content == null)
            throw new IllegalArgumentException();

        List<Comment> entities = dbc.getQueryResult(new QueryBuilder(Comment.class).addParameter("content", content).getQuery());

        return entities.get(0);
    }
    public Contact getOrganisationEntityByName(String name) {
        if (name == null)
            throw new IllegalArgumentException();

        List<Contact> entities = dbc.getQueryResult(new QueryBuilder(Contact.class).addParameter("name", name).getQuery());

        return entities.get(0);
    }
    public Employee getEmployeeEntityByName(String name) {
        if (name == null)
            throw new IllegalArgumentException();

        List<Employee> entities = dbc.getQueryResult(new QueryBuilder(Employee.class).addParameter("name", name).getQuery());

        return entities.get(0);

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

        return entities.get(0);
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
    public List<Task> getTasks(Map<String, String> filters) {
        //Find appropriate objects for filters and put them in 'parameters' map
        //e.g. <"Status","Открыта"> becomes <"Status", {StatusEntity}>
        //also it capitalizes Key (eg "sTaTUs" becomes "Status")
        Map<String, Object> parameters = new HashMap<>();
        if (filters != null && !filters.isEmpty())
            for (Map.Entry<String, String> e : filters.entrySet()) {
                if (e.getKey().length() < 2)
                    throw new IllegalArgumentException("Filter Names can't be less than 2 symbols:" + e.getKey());
                String filterName = Character.toUpperCase(e.getKey().charAt(0))+e.getKey().substring(1);

                List<Object> entities = dbc.getQueryResult(new QueryBuilder(filterName).addParameter("name", e.getValue()).getQuery());
                if (entities == null || entities.isEmpty())
                    throw new IllegalArgumentException("Invalid filter value for filter " + filterName + ": " + e.getValue());

                parameters.put(filterName.toLowerCase(), entities.get(0));
            }
        //For every filter we run a query to get tasks with selected filter and put result in list (of lists of tasks)
        List<List<Task>> tasksLists = new ArrayList<>();
        Set<Task> result = new HashSet<>();
        for (Map.Entry<String, Object> e : parameters.entrySet())
                tasksLists.add(dbc.getQueryResult(new QueryBuilder(Task.class).addParameter(e.getKey(), e.getValue()).getQuery()));
        //Now just add all tasks from all lists to set to avoid duplicates
        for (List<Task> taskList : tasksLists)
                for (Task task : taskList)
                        result.add(task);

        return new ArrayList<>(result);
    }
    public List<TaskTemplate> getAllTaskTemplates() {
        return dbc.getQueryResult(new QueryBuilder(TaskTemplate.class).getQuery());
    }
    public List<Vendor> getAllVendors() {
        return vendorsCache;
    }
}
