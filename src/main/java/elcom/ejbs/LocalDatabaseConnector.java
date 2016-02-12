package elcom.ejbs;

import elcom.Entities.*;

import javax.ejb.Local;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.io.IOException;
import java.util.ArrayList;
import java.io.Closeable;
import java.util.Date;
import java.util.List;

// Handles database data retrieving
@Singleton
@Local(DatabaseConnector.class)
public class LocalDatabaseConnector implements DatabaseConnector {
    private static final String PERSISTENCE_UNIT_NAME = "MainPersistenceUnit";
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    private static final String STATUS_FILTER_ANY = "Любой";
    private static final String EMPLOYEE_FILTER_ANY = "Все";
    private final List<Group> groupsCache;
    private final List<Priority> prioritiesCache;
    private final List<Status> statusesCache;

    public LocalDatabaseConnector() {
        prioritiesCache = cachePriorities();
        statusesCache = cacheStatuses();
        groupsCache = cacheGroups();
        }

    //Private methods used inside class
    private List<Group> cacheGroups() {
        List<Group> groups = null;

        try(DBConnection dbc = new DBConnection(emf)){
            groups = dbc.getEntityManager().createNamedQuery("select all groups").getResultList();
        } catch(Exception e){
            e.printStackTrace();
        }

        return groups;
    }
    private List<Priority> cachePriorities() {
        List<Priority> priorities = null;

        try(DBConnection dbc = new DBConnection(emf)){
            priorities = dbc.getEntityManager().createNamedQuery("select all priorities").getResultList();
        } catch(Exception e){
            e.printStackTrace();
        }

        return priorities;
    }
    private List<Status> cacheStatuses() {
        List<Status> statuses = null;

        try(DBConnection dbc = new DBConnection(emf)){
            statuses = dbc.getEntityManager().createNamedQuery("select all statuses").getResultList();
        } catch(Exception e){
            e.printStackTrace();
        }

        return statuses;
    }
    private boolean tryCopyComments(Task from, Task to) {
        List<Comment> comments = readTaskComments(from);
        for (Comment c : comments) {
            c.setTask(to);
            if (tryCreateComment(c))
                return false;
        }

        return true;
    }
    private String createQueryForReadTasks(String statusFilter, String employeeFilter) {

        String queryName;

        if (statusFilter.equals(STATUS_FILTER_ANY))
            if (employeeFilter.equals(EMPLOYEE_FILTER_ANY))
                queryName = "select all tasks";
            else
                queryName = "select tasks by employee";
        else
            if (employeeFilter.equals(EMPLOYEE_FILTER_ANY))
                queryName = "select tasks by status";
            else
                queryName = "select tasks by employee and status";

        return queryName;
    }

    public Task InstantiateTaskByTemplate(TaskTemplate tt) {
        Task newborn = new Task();

        if (tt.getCopyName().booleanValue())
            newborn.setDescription(tt.getTask().getDescription());
        if (tt.getCopyFinishDate().booleanValue())
            newborn.setFinishDate(tt.getTask().getFinishDate());
        if (tt.getCopyPriority().booleanValue())
            newborn.setPriority(tt.getTask().getPriority());
        if (tt.getCopyExecutor().booleanValue())
            newborn.setExecutor(tt.getTask().getExecutor());
        if (tt.getCopyExecutorGroup().booleanValue())
            newborn.setExecutorGroup(tt.getTask().getExecutorGroup());

        if (tt.getCopyComments().booleanValue())
            if (tryCopyComments(tt.getTask(), newborn))
                return null;

        newborn.setStartDate(new Date());

        return newborn;
    }

    // CREATE Methods
    public boolean tryCreateComment(Comment comment) {
        try (DBConnection dbc = new DBConnection(emf)) {
            dbc.getEntityManager().persist(comment);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
    public boolean tryCreateTask(Task task){
        try (DBConnection dbc = new DBConnection(emf)) {
            dbc.getEntityManager().persist(task);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
    public boolean tryCreateTaskTemplate(TaskTemplate tt) {
        try (DBConnection dbc = new DBConnection(emf)) {
            dbc.getEntityManager().persist(tt);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    // READ Methods
    public Comment readCommentByContent(String content) {
        List<Comment> comments = readAllComments();

        for (Comment d : comments)
            if (d.getContent().equals(content))
                return d;

        return null;
    }
    public Employee readEmployeeByName(String name) {
        try (DBConnection dbc = new DBConnection(emf)) {
            List<Employee> employees = dbc.getEntityManager().createNamedQuery("select all employees").getResultList();
            for (Employee e : employees)
                if (e.getFullName().toLowerCase().equals(name.toLowerCase()) || e.getName().toLowerCase().equals(name.toLowerCase()))
                    return e;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    public Group readGroupByName(String name) {
        for (Group g : groupsCache)
            if (g.getName().equals(name) || g.getFullName().equals(name))
                return g;

        return null;
    }
    public Priority readPriorityByName(String name) {
        for (Priority p: prioritiesCache)
            if (p.getName().toLowerCase().equals(name.toLowerCase()))
                return p;

        return null;
    }
    public Status readStatusByName(String name) {
        for (Status s : statusesCache)
            if (s.getName().toLowerCase().equals(name.toLowerCase()))
                return s;

        return null;
    }
    public Task readTaskById(long id) {
        Task result = null;

        try (DBConnection dbc = new DBConnection(emf)) {
            result = dbc.getEntityManager().find(Task.class, id);
        } catch(Exception e) {
            e.printStackTrace();
        }

        return result;
    }


    public List<String> readGroupsAsStrings() {
        List<String> result = new ArrayList<>();

        for (Group g : groupsCache)
            result.add(g.getFullName());

        return result;
    }
    public List<String> readEmployeesAsStrings() {
        List<String> result = null;

        try (DBConnection dbc = new DBConnection(emf)) {
            List<Employee> employees = dbc.getEntityManager().createNamedQuery("select all employees").getResultList();
            result = new ArrayList<String>();
            for (Employee e : employees)
                result.add(e.getFullName().concat(" (").concat(e.getName()).concat(")"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
    public List<String> readPrioritiesAsStrings() {
        List<String> result = new ArrayList<>();

        for (Priority p : prioritiesCache)
            result.add(p.getName());

        return result;
    }
    public List<String> readStatusesAsStrings() {
        List<String> result = new ArrayList<>();

        for (Status s : statusesCache)
            result.add(s.getName());

        return result;
    }

    public List<Comment> readAllComments() {
        List<Comment> comments = null;

        try (DBConnection dbc = new DBConnection(emf)) {
            comments = dbc.getEntityManager().createNamedQuery("select all comments").getResultList();
        } catch(Exception e) {
            e.printStackTrace();
        }

        return comments;
    }
    public List<Comment> readTaskComments(Task task) {
        if (task == null)
            throw new IllegalArgumentException("Task is null");

        List<Comment> comments = null;

        try(DBConnection dbc = new DBConnection(emf)) {
            Query query = dbc.getEntityManager().createNamedQuery("select comments by task");
            query.setParameter("task", task);
            comments = query.getResultList();
        } catch(Exception e) {
            e.printStackTrace();
        }

        return comments;
    }
    public List<Task> readTasks(String statusFilter, String employeeFilter) {
        List<Task> tasks = new ArrayList<>();

        String queryName = createQueryForReadTasks(statusFilter, employeeFilter);

        try(DBConnection dbc = new DBConnection(emf)) {
            Query query = dbc.getEntityManager().createNamedQuery(queryName);

            if (queryName.contains("status"))
                query.setParameter("status", readStatusByName(statusFilter));
            if (queryName.contains("employee"))
                query.setParameter("employee", readEmployeeByName(employeeFilter));

            tasks = query.getResultList();
        } catch(Exception e) {
            e.printStackTrace();
        }

        return tasks;
    }
    public List<TaskTemplate> readAllTaskTemplates() {
        List<TaskTemplate> taskTemplates = null;

        try (DBConnection dbc = new DBConnection(emf)) {
            taskTemplates = dbc.getEntityManager().createNamedQuery("select all task templates").getResultList();
        } catch(Exception e) {
            e.printStackTrace();
        }

        return taskTemplates;
    }

    // UPDATE Methods
    public boolean tryUpdateComment(Comment comment) {
        try (DBConnection dbc = new DBConnection(emf)) {
            dbc.getEntityManager().merge(comment);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
    public boolean tryUpdateTask(Task task) {
        try (DBConnection dbc = new DBConnection(emf)) {
            dbc.getEntityManager().merge(task);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    // DELETE Methods
    public boolean tryDeleteComment(Comment comment) {
        try (DBConnection dbc = new DBConnection(emf)) {
            dbc.getEntityManager().remove(comment);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
    public boolean tryDeleteTask(Task task) {
        List<Comment> comments = readTaskComments(task);
        for(Comment d : comments)
            if (!tryDeleteComment(d))
                return false;

        try (DBConnection dbc = new DBConnection(emf)) {
            dbc.getEntityManager().remove(task);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    //Encapsulated single Database connection. Used by LocalDatabaseConnector during transactions
    //Implements Closeable to become able to be used with try-with-resources
    private class DBConnection implements Closeable {
        private EntityManager em;

        public DBConnection(EntityManagerFactory emf) {
            em = emf.createEntityManager();
            em.getTransaction().begin();
        }

        //Used during queries to make calls to database
        public EntityManager getEntityManager() {
            return em;
        }

        @Override
        public void close() throws IOException {
            em.getTransaction().commit();
            em.close();
        }
    }
}

