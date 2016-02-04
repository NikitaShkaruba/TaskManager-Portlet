package elcom.main;

import javax.faces.bean.SessionScoped;
import javax.faces.bean.ManagedBean;
import java.text.SimpleDateFormat;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// TODO: 18.01.16 Add page interaction

// This bean handles logic from CreateTask page
@ManagedBean(name = "TaskCreator", eager=false)
@SessionScoped
public class TaskCreator {
    Task newborn = new Task("", "None", "Открыта", "Стандартный", new Date(), new Date());

    public List<String> getAllStatuses() {
        ArrayList<String> statuses = new ArrayList();
        statuses.add("Любой");
        statuses.add("Открыта");

        return statuses;
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
