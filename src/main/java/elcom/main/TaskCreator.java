package elcom.main;

import javax.faces.bean.SessionScoped;
import javax.faces.bean.ManagedBean;
import java.util.Date;
import java.util.List;

// This bean handles logic from CreateTask page
@ManagedBean(name = "TaskCreator", eager=false)
@SessionScoped
public class TaskCreator {
    Task newborn;

    public TaskCreator() {
        newborn = new Task(DatabaseConnector.getNextFreeId(), "", "None", "None", "Открыта", "Стандартный", new Date(), new Date());
    }

    // Getters
    public int getId() {
        return newborn.getId();
    }
    public String getDescription() {
        return newborn.getDescription();
    }
    public String getStatus() {
        return newborn.getStatus();
    }
    public String getGroup() {
        return newborn.getGroup();
    }
    public String getExecutor() {
        return newborn.getExecutor();
    }
    public String getPriority() {
        return newborn.getPriority();
    }
    public Date getStartDate() {
        return newborn.getStartDate();
    }
    public Date getFinishDate() {
        return newborn.getFinishDate();
    }

    // Setters
    public void setDescription(String description) {
        newborn.setDescription(description);
    }
    public void setStatus(String status) {
        newborn.setStatus(status);
    }
    public void setGroup(String group) {
        newborn.setGroup(group);
    }
    public void setExecutor(String executor) {
        newborn.setExecutor(executor);
    }
    public void setPriority(String priority) {
        newborn.setPriority(priority);
    }
    public void setStartDate(Date date) {
        newborn.setStartDate(date);
    }
    public void setFinishDate(Date date) {
        newborn.setFinishDate(date);
    }

    // logic
    public List<String> getTaskStatusOptions() {
        return DatabaseConnector.getTaskStatusOptions();
    }
    public List<String> getGroupOptions() {
        return DatabaseConnector.getGroupOptions();
    }
    public List<String> getExecutorOptions() {
        return DatabaseConnector.getEmployees();
    }
    public List<String> getPriorityOptions() {
        return DatabaseConnector.getPriorityOptions();
    }

    // Ajax listeners
    public String create() {
        DatabaseConnector.addTask(newborn);

        return "ViewTasks";
    }
}
