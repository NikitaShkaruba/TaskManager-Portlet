package elcom.mbeans;

import elcom.entities.*;
import elcom.ejbs.DataProvider;
import elcom.jpa.TasksQueryBuilder;
import elcom.tabs.*;
import elcom.tabs.Tab;
import org.primefaces.component.tabview.*;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.event.TabCloseEvent;
import javax.annotation.PostConstruct;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ManagedBean;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;

// This MBean handles selection table selection logic, provides menu item options
@ManagedBean(name = "TaskPresenter", eager=true)
@SessionScoped
public class TaskPresenter {
    private List<Tab> tabs;
    private int activeTabIndex;
    private Employee user;
    @EJB
    private DataProvider dp;

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

        // TODO: 13.02.16 Add liferay-bounded logic
        user = dp.getEmployeeEntityByName("jek");

        tabs = new ArrayList();
        tabs.add(new ListTab(allTasks));
        tabs.add(new CorrectTab(allTasks.get(0)));
    }

    private TasksQueryBuilder.TasksQuery parseFilters() {
        TasksQueryBuilder qb = new TasksQueryBuilder();

        if (!statusFilter.equals(NO_FILTER) ) {
            Status s = dp.getStatusEntityByName(statusFilter);
            if (s != null)
                qb.setStatus(s);
        }
        if (!organisationFilter.equals(NO_FILTER)) {
            Contact o = dp.getContactEntityByName(organisationFilter);
            if (o != null)
                qb.setOrganisation(o);
        }
        if (!vendorFilter.equals(NO_FILTER)) {
            Vendor v = dp.getVendorEntityByName(organisationFilter);
            if (v != null);
                //TODO: implement Vendor filter to Task
        }
        if (!groupFilter.equals(NO_FILTER)) {
            Group g = dp.getGroupEntityByName(groupFilter);
            if (g != null)
                qb.setExecutorGroup(g);
        }
        if (!executorFilter.equals(NO_FILTER)) {
            Employee e = dp.getEmployeeEntityByName(executorFilter);
            if (e != null)
                qb.setExecutor(e);
        }
        if (!creatorFilter.equals(NO_FILTER)) {
            Employee c = dp.getEmployeeEntityByName(creatorFilter);
            if (c != null)
                qb.setCreator(c);
        }

        return qb.getQuery();
    }

    // Main Logic
    public List<Task> getTasks() {
        return tabs.get(0).getTasks();
    }
    public List<Tab> getTabs() {
        return tabs;
    }

    // TODO: 18.02.16 Rewrite this method
    public String chooseRowColor(Task task) {
       if (task == null || task.getStatus() == null)
            return null;

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
    public List<Comment> getTaskComments(Task task) {
        return dp.getTaskComments(task);
    }
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
    public List<Status> getStatusOptions() {
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
    public List<Priority> getPriorityOptions() {
        return dp.getAllPriorities();
    }

    // Tabs logic
    public void onTabClose(TabCloseEvent event) {
        if (tabs.size() == 1) {
            tabs.remove(0);
            activeTabIndex = -1;
        } else {
            TabView tabView = (TabView) event.getComponent();
            activeTabIndex = tabView.getIndex();

            tabs.remove(activeTabIndex);
        }
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
    public void addListTabByFilters() {
        tabs.add(new ListTab(dp.getTasks(parseFilters())));
    }
    public Comment getNewActiveTabCommentary() {
        return (tabs.get(activeTabIndex) instanceof Commentable)? ((Commentable) tabs.get(activeTabIndex)).getNewCommentary() : null;
    }
    public void setNewActiveTabCommentary(Comment comment) {
        if (tabs.get(activeTabIndex) instanceof Commentable)
            ((Commentable) tabs.get(activeTabIndex)).setNewCommentary(comment);
    }
    public void handleFileAttachment(FileUploadEvent event) {
        // TODO: 18.02.16 add logic
    }
    public Comment getTaskComment(Task task) {
        return null;
    }

    // Proxy logic
    public Task getSelectedTask() {
        if (tabs.size() != 0 && tabs.get(activeTabIndex) instanceof TaskSelector)
            return ((TaskSelector)tabs.get(activeTabIndex)).getSelectedTask();
        else
            return null;
    }
    public void setSelectedTask(Task selectedTask) {
        if (tabs.size() != 0 && tabs.get(activeTabIndex) instanceof TaskSelector)
            ((TaskSelector)tabs.get(activeTabIndex)).setSelectedTask(selectedTask);
    }

    // TODO: 13.02.16 Add logic for pattern bar
    // Filter-pattern selection listeners
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
