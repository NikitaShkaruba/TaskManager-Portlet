package elcom.MBeans;

import elcom.Entities.Task;
import elcom.ejbs.IDatabaseConnectorLocal;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import java.util.Date;
import java.util.List;

// This bean handles logic from CreateTask page
@ManagedBean(name = "TaskCreator", eager=false)
@RequestScoped
public class TaskCreator {
    Task newborn;
    @EJB
    private IDatabaseConnectorLocal dbc;

    public TaskCreator() {
        newborn = new Task();
    }

    // Getters
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
    public void setDescription(String description) {
        newborn.setDescription(description);
    }
    public void setStatus(String status) {
        newborn.setStatus(dbc.findStatusByName(status));
    }
    public void setGroup(String group) {
        newborn.setGroup(group);
    }
    public void setExecutor(String executor) {
        newborn.setExecutor(dbc.findEmployeeByName(executor));
    }
    public void setPriority(String priority) {
        newborn.setPriority(dbc.findPriorityByName(priority));
    }
    public void setStartDate(Date date) {
        newborn.setStartDate(date);
    }
    public void setFinishDate(Date date) {
        newborn.setFinishDate(date);
    }

    // logic
    public List<String> getTaskStatusOptions() {
        return dbc.readStatuses();
    }
    public List<String> getGroupOptions() {
        return dbc.readGroups();
    }
    public List<String> getExecutorOptions() {
        return dbc.readEmployees();
    }
    public List<String> getPriorityOptions() {
        return dbc.readPriorities();
    }

    // Ajax listeners
    public void create() {
        //Display appropriate popup depending if insert was successful.
        if (dbc.tryCreateTask(newborn))
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Task was created"));
        else
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "Could not connect to database"));
    }
}
