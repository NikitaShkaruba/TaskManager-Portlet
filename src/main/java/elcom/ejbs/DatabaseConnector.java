package elcom.ejbs;

import elcom.Entities.Employee;
import elcom.Entities.Priority;
import elcom.Entities.Status;
import elcom.Entities.Task;
import elcom.enums.TaskData;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// TODO: 11.01.16 Add logic

// Handles all the information retrieving from Elcom databases
// @Local
public class DatabaseConnector {
    private static final String STATUS_ANY = "Любой";

    private final List<Status> statuses;
    private final List<Priority> priorities;
    private final List<Employee> employees;

    public DatabaseConnector() {
        List<Object> data;

        statuses = new ArrayList<Status>();
        priorities = new ArrayList<Priority>();
        employees = new ArrayList<Employee>();

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

    private List<Object> fetchData(TaskData type) {
        List<Object> result = null;
        String query = "select all ";

        switch(type) {
            case PRIORITY: query = query.concat("priorities"); break;
            case EMPLOYEE: query = query.concat("employees");  break;
            case STATUS:   query = query.concat("statuses");   break;
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
    public List<Task> readTasks(String taskFilter, String userFilter) {
        List<Task> tasks = null;

        try(DBConnection connection = new DBConnection()) {
            tasks = connection.getEntityManager().createQuery("select t from Task t").getResultList();
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        return tasks;
    }

    public Boolean addTask(Task task){
        try (DBConnection dbc = new DBConnection()) {
            dbc.getEntityManager().persist(task);
        }
        catch (Exception e ) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}

class DBConnection implements Closeable{
    private static final String DEFAULT_PERSISTANCE_UNIT_NAME = "MainPersistenceUnit";

    private EntityManagerFactory emf;
    private EntityManager em;

    public DBConnection(String PersistanceUnitName) {
        if (emf == null)
            if (PersistanceUnitName == null)
                throw new IllegalArgumentException("PersistanceUnitName is null");
            else
                emf = Persistence.createEntityManagerFactory(PersistanceUnitName);

        em = emf.createEntityManager();
        em.getTransaction().begin();
    }
    public DBConnection() {
        this(DEFAULT_PERSISTANCE_UNIT_NAME);
    }

    public EntityManager getEntityManager() {
        return em;
    }

    @Override
    public void close() throws IOException {
        em.getTransaction().commit();
        em.close();
    }
}