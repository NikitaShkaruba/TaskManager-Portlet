package elcom.mbeans;

import elcom.entities.*;

import javax.faces.context.FacesContext;
import elcom.tabs.*;
import elcom.ejbs.DatabaseConnector;
import org.primefaces.event.SelectEvent;
import javax.annotation.PostConstruct;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ManagedBean;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;

// This MBean handles logic from ViewTasks page
@ManagedBean(name = "TaskPresenter", eager=true)
@SessionScoped
public class TaskPresenter {
    private List<Tab> tabs;
    private List<Task> tasks;
    private Employee user;  //// TODO: 13.02.16 Add liferay-bounded logic
    @EJB
    private DatabaseConnector dbc;
    private Task selectedTask;

    private String statusFilter;
    private String organizationFilter;
    private String vendorFilter;
    private String groupFilter;
    private String executorFilter;
    private String creatorFilter;
    private String descriptionFilter;

    public TaskPresenter() {
        statusFilter = "-- все --";
        organizationFilter = "-- все --";
        vendorFilter = "-- все --";
        groupFilter = "-- все --";
        executorFilter = "-- все --";
        creatorFilter = "-- все --";
    }
    // Cannot move tasks initialization to a constructor coz ejb injections occurs after constructor
    @PostConstruct
    public void init() {
        List<Task> allTasks = dbc.readTasks(this.statusFilter, "Все");

        tabs = new ArrayList();
        tabs.add(new ListTab(allTasks));
        tabs.add(new ChangeTab(allTasks.get(0)));
        tabs.add(new ChangeTab(allTasks.get(1)));
    }

    // Main Logic
    public List<Task> getTasks() {
        return tabs.get(0).getTasks();
    }
    public List<Tab> getTabs() {
        return tabs;
    }
    public List<Task> getSomeTasks() {
        return getTasks().subList(1, 5);
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
    public boolean isNewToUser(Task task) {
        // TODO: 13.02.16 add liferay-bounded logic
        return true;
    }
    public int getTasksAmount() {
        return tasks.size();
    }
    private long getSelectedTaskId() {
        return selectedTask.getId();
    }
    public void setSelectedTask(Task selectedTask) {
        this.selectedTask = selectedTask;
    }

    // Getters
    public String getStatusFilter() {
        return statusFilter;
    }
    public Task getSelectedTask() {
        return selectedTask;
    }
    public String getOrganizationFilter() {
        return organizationFilter;
    }
    public String getVendorFilter() {
        return vendorFilter;
    }
    public String getGroupFilter() {
        return groupFilter;
    }
    public String getExecutorFilter() {
        return executorFilter;
    }
    public String getCreatorFilter() {
        return creatorFilter;
    }
    public String getDescriptionFilter() {
        return descriptionFilter;
    }

    // Setters
    public void setStatusFilter(String filter) {
        this.statusFilter = filter;
    }
    public void setOrganizationFilter(String organizationFilter) {
        this.organizationFilter = organizationFilter;
    }
    public void setVendorFilter(String vendorFilter) {
        this.vendorFilter = vendorFilter;
    }
    public void setGroupFilter(String groupFilter) {
        this.groupFilter = groupFilter;
    }
    public void setExecutorFilter(String executorFilter) {
        this.executorFilter = executorFilter;
    }
    public void setCreatorFilter(String creatorFilter) {
        this.creatorFilter = creatorFilter;
    }
    public void setDescriptionFilter(String descriptionFilter) {
        this.descriptionFilter = descriptionFilter;
    }

    // Lists for GUI MenuOptions
    public List<Employee> getEmployeeOptions() {
        return dbc.readAllEmployees();
    }
    public List<Status> getStatusesOptions() {
        return dbc.readAllStatuses();
    }
    public List<Contact> getOrganizationOptions() {
        return dbc.readAllOrganizations();
    }
    public List<Vendor> getVendorOptions() {
        return dbc.readAllVendors();
    }
    public List<Group> getGroupOptions() {
        return dbc.readAllGroups();
    }

    // AJAX Listeners
    public void viewMore() {
        tabs.add(new MoreTab(getTasks().get(6)));
    }
    public void createTask() {
        tabs.add(new CreateTab());
    }
    public void selectNewStatusFilter() {
        // TODO: 13.02.16 Remove plug
        tasks = dbc.readTasks(statusFilter, "Все");
    }
    public void selectNewCreatorFilter() {
        // TODO: 13.02.16 Fill with logic
    }
    public void selectNewExecutorFilter() {
        // TODO: 13.02.16 Fill with logic
    }
    public void selectNewVendorFilter() {
        // TODO: 13.02.16 Fill with logic
    }
    public void selectNewOrganizationFilter() {
        // TODO: 13.02.16 Fill with logic
    }
    public void selectNewGroupFilter() {
        // TODO: 13.02.16 Fill with logic
    }
    public void selectNewDescriptionFilter() {
        // TODO: 13.02.16 Fill with logic
    }
    public void onRowSelect(SelectEvent event) {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("CorrectTask.xhtml?id=" + selectedTask.getId());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // TODO: 13.02.16 Add logic
    public void selectMyTasksPattern() {

    }
    public void selectFreeTasksPattern() {

    }
    public void selectClosedTasksPattern() {

    }
    public void selectTrackedTasksPattern() {

    }
    public void selectChangedTasksPattern() {

    }
    public void selectContractsTaskPattern() {

    }
    public int getMyCount() {
        return 2;
    }
    public int getOpenCount() {
        return 45;
    }
    public int getClosedCount() {
        return 78;
    }
    public int getTrackedCount() {
        return 32;
    }
    public int getChangedCount() {
        return 333;
    }
    public int getContractsCount() {
        return 1;
    }
}
