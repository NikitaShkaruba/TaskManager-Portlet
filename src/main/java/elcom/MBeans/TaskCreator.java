package elcom.MBeans;

import elcom.Entities.Employee;
import elcom.Entities.Status;
import elcom.Entities.Task;
import elcom.ejbs.DatabaseConnector;
import elcom.enums.TaskData;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.faces.bean.SessionScoped;
import javax.faces.bean.ManagedBean;
import java.util.Date;
import java.util.List;

// This bean handles logic from CreateTask page
@ManagedBean(name = "TaskCreator", eager=false)
@SessionScoped
public class TaskCreator {
    Task newborn;
    DatabaseConnector dbc;

    public TaskCreator() {
        //TODO: remove dbc constructor for injection
        dbc = new DatabaseConnector();

        newborn = new Task();
    }

    // Getters
    public int getId() {
        return newborn.getId();
    }
    public String getDescription() {
        return newborn.getDescription() == null ? "???" : newborn.getDescription();
    }
    public String getStatus() {
        return newborn.getStatus() == null ? "???" : newborn.getStatus().getName();
    }
    public String getGroup() {
        return newborn.getGroup() == null ? "???" : newborn.getGroup();
    }
    public String getExecutor() {
        return newborn.getExecutor() == null ? "???" : newborn.getExecutor().getName();
    }
    public String getPriority() {
        return newborn.getPriority() == null ? "???" : newborn.getPriority().getName();
    }
    public Date getStartDate() {
        return newborn.getStartDate();
    }
    public Date getFinishDate() {
        return newborn.getFinishDate();
    }

    // Setters
    //TODO: Fix broken setters when adding new task.
    public void setDescription(String description) {
        newborn.setDescription(description);
    }
    public void setStatus(String status) {
        newborn.setStatus(new Status());
    }
    public void setGroup(String group) {
        newborn.setGroup(group);
    }
    public void setExecutor(String executor) {
        newborn.setExecutor(new Employee());
    }
    public void setPriority(String priority) {
        throw new NotImplementedException();
    }
    public void setStartDate(Date date) {
        newborn.setStartDate(date);
    }
    public void setFinishDate(Date date) {
        newborn.setFinishDate(date);
    }

    // logic
    public List<String> getTaskStatusOptions() {
        return dbc.retrieveData(TaskData.STATUS);
    }
    public List<String> getGroupOptions() {
        return dbc.retrieveData(TaskData.GROUP);
    }
    public List<String> getExecutorOptions() {
        return dbc.retrieveData(TaskData.EMPLOYEE);
    }
    public List<String> getPriorityOptions() {
        return dbc.retrieveData(TaskData.PRIORITY);
    }

    // Ajax listeners
    public String create() {
        dbc.addTask(newborn);

        return "ViewTasks";
    }
}
