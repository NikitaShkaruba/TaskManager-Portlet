package elcom.mbeans;

import javax.faces.application.FacesMessage;
import javax.faces.bean.NoneScoped;
import javax.faces.context.FacesContext;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import elcom.entities.*;
import elcom.ejbs.DatabaseConnector;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;

// This bean handles logic from CreateTask page
@ManagedBean(name = "TaskEditor", eager=false)
@NoneScoped
public class TaskEditor {
    Task task;
    @EJB
    private DatabaseConnector dbc;

    public TaskEditor() {
        task = new Task();
    }
    // These methods get called before the page rendering
    public void initializeSelectedForEditTask(int id) {
        task = dbc.readTaskById(id);
    }
    public void initializeTaskForCreation() {
        task.setStatus(dbc.readStatusByName("Открыта"));
        task.setPriority(dbc.readPriorityByName("Низкий"));
        task.setExecutor(dbc.readEmployeeByName("Jek"));
    }

    // Getters
    public String getDescription() {
        return task.getDescription();
    }
    public String getStatus() {
        return task.getStatus().getName();
    }
    public String getGroup() {
        return task.getExecutorGroup().getFullName();
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
        task.setStatus(dbc.readStatusByName(status));
    }
    public void setGroup(String group) {
        task.setExecutorGroup(dbc.readGroupByName(group));
    }
    public void setExecutor(String executor) {
        task.setExecutor(dbc.readEmployeeByName(executor));
    }
    public void setPriority(String priority) {
        task.setPriority(dbc.readPriorityByName(priority));
    }
    public void setStartDate(Date date) {
        task.setStartDate(date);
    }
    public void setFinishDate(Date date) {
        task.setFinishDate(date);
    }

    // logic
    public List<String> getTaskStatusOptions() {
        List<String> result = new ArrayList<>();

        for (Status s : dbc.readAllStatuses())
            result.add(s.toString());

        return result;
    }
    public List<String> getGroupOptions() {
        List<String> result = new ArrayList<>();

        for (Group g: dbc.readAllGroups())
            result.add(g.toString());

        return result;
    }
    public List<String> getExecutorOptions() {
        List<String> result = new ArrayList<>();

        for (Employee e: dbc.readAllEmployees())
            result.add(e.toString());

        return result;
    }
    public List<String> getPriorityOptions() {
        List<String> result = new ArrayList<>();

        for (Priority p: dbc.readAllPriorities())
            result.add(p.toString());

        return result;
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
