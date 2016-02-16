package elcom.MBeans;

import elcom.Entities.*;

import javax.faces.context.FacesContext;

import elcom.ejbs.DataProvider;
import org.primefaces.event.SelectEvent;
import javax.annotation.PostConstruct;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ManagedBean;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;

// This MBean handles logic from ViewTasks page
@ManagedBean(name = "TaskPresenter", eager=true)
@SessionScoped
public class TaskPresenter {
    private List<Task> tasks;
    private Employee user;  //// TODO: 13.02.16 Add liferay-bounded logic
    @EJB
    private DataProvider dp;
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
        tasks = dp.getAllTasks();
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
        return dp.getAllEmployees();
    }
    public List<Status> getStatusesOptions() {
        return dp.getAllStatuses();
    }
    public List<Contact> getOrganizationOptions() {
        return dp.getAllOrganisations();
    }
    public List<Vendor> getVendorOptions() {
        return dp.getAllVendors();
    }
    public List<Group> getGroupOptions() {
        return dp.getAllGroups();
    }

    // AJAX Listeners

    public void selectNewStatusFilter() {
        Map<String, String> filters = new HashMap<>();

        filters.put("Status", statusFilter);
        tasks = dp.getTasks(filters);
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
