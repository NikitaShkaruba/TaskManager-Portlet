package elcom.ejbs;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import elcom.enums.TaskData;
import javax.ejb.Singleton;
import java.io.IOException;
import java.util.ArrayList;
import java.io.Closeable;
import elcom.Entities.*;
import java.util.Date;
import java.util.List;

// Handles database data retrieving
@Singleton
public class DatabaseConnector implements IDatabaseConnectorLocal {
    // Query constants
    private static final String STATUS_ANY = "Любой";
    private static final String EMPLOYEE_ANY = "Все";
    // Caches
    private final List<Status> statuses;
    private final List<Priority> priorities;
    private final List<Employee> employees;

    public DatabaseConnector() {
        List<Object> data;

        statuses = new ArrayList<>();
        priorities = new ArrayList<>();
        employees = new ArrayList<>();

        data = fetchData(TaskData.STATUS);
        for (Object status : data)
            statuses.add((Status)status);

        data = fetchData(TaskData.PRIORITY);
        for (Object priority : data)
            priorities.add((Priority)priority);

        data = fetchData(TaskData.EMPLOYEE);
        for (Object employee : data)
            employees.add((Employee)employee);
    }

    // Methods used inside class to make code more readable
    private List<Object> fetchData(TaskData type) {
        List<Object> result = null;
        String query;

        switch(type) {
            case PRIORITY: query = "select all priorities"; break;
            case EMPLOYEE: query = "select all employees";  break;
            case STATUS:   query = "select all statuses";   break;
            case GROUP: throw new NotImplementedException();
            default:    throw new NotImplementedException();
        }

        try(DBConnection connection = new DBConnection()){
            result = connection.getEntityManager().createNamedQuery(query).getResultList();
        }
        catch(Exception e){
            e.printStackTrace();
        }

        return result;
    }
    private String createReadTaskQuery(String statusFilter, String employeeFilter) {

        String queryName;

        if (statusFilter.equals(STATUS_ANY))
            if (employeeFilter.equals(EMPLOYEE_ANY))
                queryName = "select all tasks";
            else
                queryName = "select tasks by employee";
        else
            if (employeeFilter.equals(EMPLOYEE_ANY))
                queryName = "select tasks by status";
            else
                queryName = "select tasks by employee and status";

        return queryName;
    }
    // Methods used to get full entity instance (id-name-etc) having just name.
    public Status findStatusByName(String name) {
        for (Status s : statuses)
            if (s.getName().toLowerCase().equals(name.toLowerCase()))
                return s;
        return null;
    }
    public Employee findEmployeeByName(String name) {
        for (Employee e : employees)
            if (    e.getFullName().toLowerCase().equals(name.toLowerCase())
                    || e.getName().toLowerCase().equals(name.toLowerCase()))
                return e;
        return null;
    }
    public Priority findPriorityByName(String name) {
        for (Priority p: priorities)
            if (p.getName().toLowerCase().equals(name.toLowerCase()))
                return p;
        return null;
    }
    public Task findTaskById(int id) {
        Task t = new Task();

        t.setStatus(findStatusByName("Открыта"));
        t.setPriority(findPriorityByName("Низкий"));
        t.setExecutor(findEmployeeByName("Jek"));
        t.setDescription("sht");
        t.setGroup("plug");
        t.setFinishDate(new Date());
        t.setStartDate(new Date());

        return t;
    }
    // CREATE Methods
    public Boolean tryCreateTask(Task task){
        try (DBConnection dbc = new DBConnection()) {
            dbc.getEntityManager().persist(task);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    // READ Methods
    public List<String> readData(TaskData type) {
        List<String> result = new ArrayList<String>();

        switch (type){
            case EMPLOYEE: for (Employee e : employees)  result.add(e.getFullName()); break;
            case PRIORITY: for (Priority p : priorities) result.add(p.getName());     break;
            case STATUS:   for (Status s : statuses)     result.add(s.getName());     break;
            case GROUP:    result.add("все сотрудники");                              break;
            default:       throw new NotImplementedException();
        }

        return result;
    }
    public List<Task> readTasks(String statusFilter, String employeeFilter) {
        List<Task> tasks = null;

        String queryName = createReadTaskQuery(statusFilter, employeeFilter);

        try(DBConnection dbc = new DBConnection()) {
            Query query = dbc.getEntityManager().createNamedQuery(queryName);

            if (queryName.contains("status"))
                query.setParameter("status", findStatusByName(statusFilter));
            if (queryName.contains("employee"))
                query.setParameter("employee", findEmployeeByName(employeeFilter));

            tasks = query.getResultList();
        } catch(Exception e) {
            e.printStackTrace();
        }

        return tasks;
    }

    // UPDATE Methods
    public Boolean tryUpdateTask(Task task) {
        try (DBConnection dbc = new DBConnection()) {
            dbc.getEntityManager().merge(task);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    // Encapsulated single Database connection. Used by DatabaseConnector during transactions
    // Implements Closeable to become able to be used with try-with-resources
    private class DBConnection implements Closeable {
        private static final String DEFAULT_PERSISTENCE_UNIT_NAME = "MainPersistenceUnit";
        private EntityManagerFactory emf;
        private EntityManager em;

        public DBConnection(String PersistenceUnitName) {
            if (emf == null)
                if (PersistenceUnitName == null)
                    throw new IllegalArgumentException("PersistenceUnitName is null");
                else
                    emf = Persistence.createEntityManagerFactory(PersistenceUnitName);

            em = emf.createEntityManager();
            em.getTransaction().begin();
        }
        public DBConnection() {
            this(DEFAULT_PERSISTENCE_UNIT_NAME);
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

