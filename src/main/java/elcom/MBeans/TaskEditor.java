package elcom.MBeans;

import javax.faces.application.FacesMessage;
import elcom.ejbs.IDatabaseConnectorLocal;
import javax.faces.context.FacesContext;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import elcom.enums.TaskData;
import elcom.Entities.Task;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;

// This bean handles logic from CreateTask page
@ManagedBean(name = "TaskEditor", eager=false)
@ViewScoped
public class TaskEditor {
    Task task;
    @EJB
    private IDatabaseConnectorLocal dbc;

    public TaskEditor() {
        task = new Task();
    }
    // These methods get called before the page rendering
    public void initializeSelectedForEditTask(int id) {
        task = dbc.findTaskById(id);
    }
    public void initializeTaskForCreation() {
        task.setStatus(dbc.findStatusByName("Открыта"));
        task.setPriority(dbc.findPriorityByName("Низкий"));
        task.setExecutor(dbc.findEmployeeByName("Jek"));
    }

    // Getters
    public String getDescription() {
        return task.getDescription();
    }
    public String getStatus() {
        return task.getStatus().getName();
    }
    public String getGroup() {
        return task.getGroup();
    }
    public String getExecutor() {
        return task.getExecutor().getName();
    }
    public String getPriority() {
        return task.getPriority().getName();
    }
    public Date getStartDate() {
        return task.getStartDate();
    }
    public Date getFinishDate() {
        return task.getFinishDate();
    }

    // Setters
    public void setDescription(String description) {
        task.setDescription(description);
    }
    public void setStatus(String status) {
        task.setStatus(dbc.findStatusByName(status));
    }
    public void setGroup(String group) {
        task.setGroup(group);
    }
    public void setExecutor(String executor) {
        task.setExecutor(dbc.findEmployeeByName(executor));
    }
    public void setPriority(String priority) {
        task.setPriority(dbc.findPriorityByName(priority));
    }
    public void setStartDate(Date date) {
        task.setStartDate(date);
    }
    public void setFinishDate(Date date) {
        task.setFinishDate(date);
    }

    // logic
    public List<String> getTaskStatusOptions() {
        return dbc.readData(TaskData.STATUS);
    }
    public List<String> getGroupOptions() {
        return dbc.readData(TaskData.GROUP);
    }
    public List<String> getExecutorOptions() {
        return dbc.readData(TaskData.EMPLOYEE);
    }
    public List<String> getPriorityOptions() {
        return dbc.readData(TaskData.PRIORITY);
    }

    // Ajax listeners
    public void create() {
        //Display appropriate popup depending if insert was successful.
        if (dbc.tryCreateTask(task))
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Task was created"));
        else
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "Could not connect to database"));
    }
    public void correct() {
        //Display appropriate popup depending if insert was successful.
        if (dbc.tryUpdateTask(task))
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Task was updated"));
        else
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "Could not connect to database"));
    }
}
