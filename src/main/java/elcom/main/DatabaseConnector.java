package elcom.main;

import elcom.Entities.Task;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

// TODO: 11.01.16 Add logic

// Handles all the information retrieving from Elcom databases
// @Local
public class DatabaseConnector {
    private static final String PERSISTENCE_UNIT_NAME = "MainPersistenceUnit";
    private static EntityManagerFactory emf;
    private static EntityManager em;

    private DatabaseConnector() {}

    private static void startConnection(){
        if (emf == null)
            emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        em = emf.createEntityManager();
        em.getTransaction().begin();
    }
    private static void closeConnection(){
        em.getTransaction().commit();
        em.close();
    }

    public static List<String> getTaskStatusOptions() {
        List<String> options = new ArrayList<>();

        options.add("Выполнена");
        options.add("Выполняется");
        options.add("Открыта");
        options.add("Закрыта");
        options.add("Отложена");
        options.add("Шаблон");
        options.add("Отменена");

        return options;
    }
    public static List<String> getEmployees() {
        ArrayList temp = new ArrayList<String>();
        temp.add("Анатолий Приблуда");
        temp.add("Евгений Цопа");

        return temp;
    }
    public static List<String> getPriorityOptions() {
        ArrayList temp = new ArrayList<String>();
        temp.add("Критический");
        temp.add("Очень высокий");
        temp.add("Высокий");
        temp.add("Средний");
        temp.add("Низкий");
        temp.add("Очень низкий");
        temp.add("В рабочем порядке");

        return temp;
    }
    public static List<String> getGroupOptions() {
        // no orders for logic for now, just as it is.
        ArrayList<String> groups = new ArrayList();
        groups.add("все сотрудники");

        return groups;
    }

    // Tasks interaction
    public static List<Task> getTasks(String taskFilter, String userFilter) {
        startConnection();
        List<Task> tasks = em.createQuery("select t from Task t").getResultList();
        System.out.println("id = ".concat(new Integer(tasks.get(0).getId()).toString())
                .concat("; desc = ").concat(tasks.get(0).getDescription() == null ? "null" : tasks.get(0).getDescription())
                .concat("; exec = ").concat(tasks.get(0).getExecutor() == null ? "null" : tasks.get(0).getExecutor().toString())
                .concat("; group = ").concat(tasks.get(0).getGroup() == null ? "null" : tasks.get(0).getGroup())
                .concat("; status = ").concat(tasks.get(0).getStatus() == null ? "null" : tasks.get(0).getStatus().toString())
                .concat("; prio = ").concat(tasks.get(0).getPriority() == null ? "null" : tasks.get(0).getPriority().toString())
                .concat("; BegDate = ").concat(tasks.get(0).getStartDate() == null ? "null" : tasks.get(0).getStartDate().toString())
                .concat("; FinDate = ").concat(tasks.get(0).getFinishDate() == null ? "null" : tasks.get(0).getFinishDate().toString()));
        closeConnection();

        return tasks;
    }
    public static void addTask(Task task) {
        // logic
    }
    public static void removeTask(Task task) {
        throw new NotImplementedException();
    }

    // TODO: 06.02.16 Enhance this method
    public static int getNextFreeId() {
        return 100500;
    }
}
