package elcom.mbeans;

import elcom.entities.*;

import elcom.ejbs.DataProvider;
import elcom.tabs.*;
import org.primefaces.event.TabCloseEvent;

import javax.annotation.PostConstruct;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ManagedBean;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;

// This MBean handles logic from ViewTasks page
@ManagedBean(name = "TaskPresenter", eager=true)
@SessionScoped
public class TaskPresenter {
    private List<Tab> tabs;
    private List<Task> tasks;
    private Employee user;  //// TODO: 13.02.16 Add liferay-bounded logic
    @EJB
    private DataProvider dp;
    private Task selectedTask;

    private static final String NO_FILTER = "-- все --";

    private String statusFilter;
    private String organisationFilter;
    private String vendorFilter;
    private String groupFilter;
    private String executorFilter;
    private String creatorFilter;
    private String descriptionFilter;

    public TaskPresenter() {
        statusFilter = NO_FILTER;
        organisationFilter = NO_FILTER;
        vendorFilter = NO_FILTER;
        groupFilter = NO_FILTER;
        executorFilter = NO_FILTER;
        creatorFilter = NO_FILTER;
        descriptionFilter = NO_FILTER;
    }
    // Cannot move tasks initialization to a constructor coz ejb injections occurs after constructor
    @PostConstruct
    public void init() {
        List<Task> allTasks = dp.getAllTasks();

        tabs = new ArrayList();
        tabs.add(new ListTab(allTasks));
        tabs.add(new ChangeTab(allTasks.get(0)));
        tabs.add(new ChangeTab(allTasks.get(1)));
    }

    private Map<String, String> combineFilters() {
        Map<String, String> filters = new HashMap<>();

        if (!statusFilter.equals(NO_FILTER)) filters.put("status", statusFilter);
        if (!organisationFilter.equals(NO_FILTER)) filters.put("organisation", organisationFilter);
        if (!vendorFilter.equals(NO_FILTER)) filters.put("vendor", vendorFilter);
        if (!groupFilter.equals(NO_FILTER)) filters.put("executorGroup", groupFilter);
        if (!executorFilter.equals(NO_FILTER)) filters.put("executor", executorFilter);
        if (!creatorFilter.equals(NO_FILTER)) filters.put("creator", creatorFilter);

        return filters;
    }

    // Main Logic
    public List<Task> getTasks() {
        return tabs.get(0).getTasks();
    }
    public List<Tab> getTabs() {
        return tabs;
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

    // Getters
    public String getStatusFilter() {
        return statusFilter;
    }
    public String getOrganisationFilter() {
        return organisationFilter;
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
    public void setOrganisationFilter(String organisationFilter) {
        this.organisationFilter = organisationFilter;
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
    public void viewMore() {
        tabs.add(new MoreTab(getTasks().get(6)));
    }
    public void createTask() {
        tabs.add(new CreateTab());
    }
    public void selectNewStatusFilter() {
        Map<String, String> filters = combineFilters();
        tasks = dp.getTasks(filters);
    }
    public void selectNewCreatorFilter() {
        Map<String, String> filters = combineFilters();
        tasks = dp.getTasks(filters);
    }
    public void selectNewExecutorFilter() {
        Map<String, String> filters = combineFilters();
        tasks = dp.getTasks(filters);
    }
    public void selectNewVendorFilter() {
        //TODO: Add vendor field to Task?
    }
    public void selectNewOrganizationFilter() {
        Map<String, String> filters = combineFilters();
        tasks = dp.getTasks(filters);
    }
    public void selectNewGroupFilter() {
        Map<String, String> filters = combineFilters();
        tasks = dp.getTasks(filters);
    }
    public void selectNewDescriptionFilter() {
        List<Task> allTasks = dp.getAllTasks();
        for(Task t : allTasks)
            if (!t.getDescription().contains(descriptionFilter))
                allTasks.remove(t);

        tasks = allTasks;
    }
    public void onTabClose(TabCloseEvent event) {
        tabs.remove(Integer.valueOf(event.getTab().getId()));
    }

    // TODO: 13.02.16 Add logic
    // TODO: Learn to write better TODO's. I have no idea what these methods are.
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
