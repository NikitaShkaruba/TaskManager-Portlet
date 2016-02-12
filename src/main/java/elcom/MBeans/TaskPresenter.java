package elcom.MBeans;

import elcom.ejbs.IDatabaseConnectorLocal;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;
import javax.annotation.PostConstruct;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ManagedBean;
import elcom.Entities.Task;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;

// This MBean handles logic from ViewTasks page
@ManagedBean(name = "TaskPresenter", eager=true)
@SessionScoped
public class TaskPresenter {
    private List<Task> tasks;
    private String selectedTaskFilter;
    private String selectedEmployeeFilter;
    private Task selectedTask;
    @EJB
    private IDatabaseConnectorLocal dbc;

    public TaskPresenter() {
        selectedTaskFilter = "Любой";
        selectedEmployeeFilter = "Все";
    }
    // Cannot move tasks initialization to a constructor coz ejb injections occurs after constructor
    @PostConstruct
    public void init() {
        tasks = dbc.readTasks(this.selectedTaskFilter, this.selectedEmployeeFilter);
    }

    // Main Logic
    public List<Task> getTasks() {
        return tasks;
    }
    public String chooseRowColor(Task task) {
       switch (task.getStatus().getName()) {
           case "открыта": return "Red";
           case "закрыта": return "Green";
           case "отменена": return "Green";
           case "отложена": return "Blue";
           case "шаблон": return "Black";
           case "выполнена": return "Green";
           case "выполняется": return "Brown";

           default: return null;
       }
    }
    // Getters
    public String getSelectedTaskFilter() {
        return selectedTaskFilter;
    }
    public Task getSelectedTask() {
        return selectedTask;
    }
    public String getSelectedEmployeeFilter() {
        return selectedEmployeeFilter;
    }

    // Setters
    public void setSelectedTaskFilter(String filter) {
        this.selectedTaskFilter = filter;
    }
    public void setSelectedEmployeeFilter(String filter) {
        this.selectedEmployeeFilter = filter;
    }
    public void setSelectedTask(Task selectedTask) {
        this.selectedTask = selectedTask;
    }

    // Lists for GUI MenuOptions
    public List<String> getEmployeeFilterOptions() {
        List<String> options = new ArrayList<>();

        options.add("Мои");
        options.add("Все");

        return options;
    }
    public List<String> getTaskStatusesOptions() {
        return dbc.readStatuses();
    }

    // AJAX Listeners
    public void selectNewTaskFilter() {
        tasks = dbc.readTasks(selectedTaskFilter, selectedEmployeeFilter);
    }
    public void selectNewEmployeeFilter() {
        tasks = dbc.readTasks(selectedTaskFilter, selectedEmployeeFilter);
    }
    public void onRowSelect(SelectEvent event) {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("CorrectTask.xhtml?id=" + selectedTask.getId());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
