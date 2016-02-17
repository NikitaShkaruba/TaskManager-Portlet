package elcom.mbeans;

import elcom.entities.*;
import elcom.ejbs.DataProvider;
import elcom.tabs.*;
import elcom.tabs.Tab;
import org.primefaces.component.tabview.*;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.event.TabCloseEvent;
import javax.annotation.PostConstruct;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ManagedBean;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;

// This MBean handles selection table selection logic, provides menu item options
@ManagedBean(name = "TaskPresenter", eager=true)
@SessionScoped
public class TaskPresenter {
    private List<Tab> tabs;
    private Employee user;  //// TODO: 13.02.16 Add liferay-bounded logic
    @EJB
    private DataProvider dp;
    private int activeTabIndex;

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
        List<Task> allTasks = dp.getAllTasks();

        tabs = new ArrayList();
        tabs.add(new ListTab(allTasks));
        tabs.add(new CorrectTab(allTasks.get(0)));
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

    // Getters
    public String getStatusFilter() {
        return statusFilter;
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

    // Filter change listeners
    public void selectNewStatusFilter() {
        Map<String, String> filters = new HashMap<>();
        filters.put("Status", statusFilter);
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

    // Tabs logic
    public void onTabClose(TabCloseEvent event) {
        if (tabs.size() == 1) {
            tabs.remove(0);
            activeTabIndex = -1;
        } else
            tabs.remove(activeTabIndex);
    }
    public void onTabChange(TabChangeEvent event) {
        TabView tabView = (TabView) event.getComponent();
        activeTabIndex = tabView.getIndex();
    }
    public void addMoreTab(Task content) {
        tabs.add(new MoreTab(content));
    }
    public void addCreateTab() {
        tabs.add(new CreateTab());
    }
    public void addCorrectTab(Task content) {
        tabs.add(new CorrectTab(content));
    }
    public void addListTab() {
        // TODO: 17.02.16 add logic
    }

    // Proxy logic
    public Task getSelectedTask() {
        if (tabs.get(activeTabIndex) instanceof TaskSelector)
            return ((TaskSelector)tabs.get(activeTabIndex)).getSelectedTask();
        else
            return null;
    }
    public void setSelectedTask(Task selectedTask) {
        if (tabs.get(activeTabIndex) instanceof TaskSelector)
            ((TaskSelector)tabs.get(activeTabIndex)).setSelectedTask(selectedTask);
    }

    // TODO: 13.02.16 Add logic
    // Filter-pattern selection lsteners
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

    // Filter-pattern task counts
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
