package elcom.main;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import javax.faces.bean.ManagedBean;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

// TODO: 11.01.16 Add logic

// Handles all the information retrieving from Elcom databases
// @Local
class DatabaseConnector {
    private EntityManagerFactory emf;
    private EntityManager em;

    private DatabaseConnector() {}

    private void startConnection(){
        emf = Persistence.createEntityManagerFactory("MainPersistenceUnit");
        em = emf.createEntityManager();
        em.getTransaction().begin();
    }
    private void closeConnection(){
        em.getTransaction().commit();
        emf.close();
    }

    public EntityManager getEntityManager(){
        return em;
    }
    // Get options
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
        ArrayList temp = new ArrayList<Task>();

        // TODO: 03.02.16 add taskFilter logic
        if (userFilter.equals("Все")) {
            temp.add(new Task(1, "Give us some water", "Daneeil", "Открыта", "Обычный"));
            temp.add(new Task(2, "Give us food", "Daneeil", "Открыта", "Обычный"));
            temp.add(new Task(3, "Give us place to sleep", "Daneeil", "Открыта", "Обычный"));
        } else
            temp.add(new Task(1, "Give us some water", userFilter, "Открыта", "Обычный"));

        for (int i = 0; i < 200; i++)
            temp.add(new Task(i, "Plug", "Plug", "Plug", "Plug"));

        return temp;
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
