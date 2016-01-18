package elcom.main;


import javax.faces.bean.SessionScoped;
import javax.faces.bean.ManagedBean;
import java.text.SimpleDateFormat;
import elcom.enums.TaskPriority;
import elcom.enums.TaskStatus;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Nikita Shkaruba on 18.01.16.
 * <p>
 * My Contacts:
 * Email: sh.nickita@list.ru
 * GitHub: github.com/SigmaOne
 * Vk: vk.com/wavemeaside
 */

// TODO: 18.01.16 Add page interaction

// This bean handles logic from create-task page
@ManagedBean(name = "TaskCreator")
@SessionScoped
public class TaskCreator {
    Task newborn = new Task("", "None", TaskStatus.OPEN, TaskPriority.NORMAL, new Date(), new Date());

    public List<String> getAllStatuses() {
        ArrayList<String> statuses = new ArrayList();

        for (TaskStatus status: TaskStatus.values())
            statuses.add(status.toString().toLowerCase());

        return statuses;
    }
    public List<String> getAllSPriorities() {
        ArrayList<String> priorities = new ArrayList();

        for (TaskPriority status: TaskPriority.values())
            priorities.add(status.toString().toLowerCase());

        return priorities;
    }
    public List<String> getAllGroups() {
        // no orders for logic for now, just as it is.
        ArrayList<String> groups = new ArrayList();
        groups.add("все сотрудники");

        return groups;
    }
    public List<String> getAllExecutors() {
        return new DatabaseConnector().getEmployees();
    }
    public String getStartDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return dateFormat.format(newborn.getStartDate());
    }
    public String getFinishDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return dateFormat.format(newborn.getFinishDate());
    }

    public void create() {
        DatabaseConnector databaseConnector = new DatabaseConnector();
        databaseConnector.addTask(newborn);
    }
}
