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
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.annotation.PostConstruct;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ManagedBean;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.event.ActionEvent;

// This MBean handles selection table selection logic, provides menu item options
@ManagedBean(name = "TaskPresenter", eager=true)
@SessionScoped
public class TaskPresenter {
    private List<Tab> tabs;
    private int activeTabIndex;
    private Employee user;
    @EJB
    private DataProvider dp;

    private static final String NO_FILTER = "-- Все --";

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
        descriptionFilter = "";
    }
    // Cannot move tasks initialization to a constructor coz ejb injections occurs after constructor
    @PostConstruct
    public void init() {
        List<Task> allTasks = dp.getAllTasks();

        // TODO: 13.02.16 Add liferay-bounded logic
        user = dp.getEmployeeEntityByName("Evgenij Tsopa");

        tabs = new ArrayList();
        tabs.add(new ListTab(allTasks));
    }

    private TasksQueryBuilder.TasksQuery parseFilters() {
        TasksQueryBuilder qb = new TasksQueryBuilder();

        if (!statusFilter.equals(NO_FILTER) ) {
            Status s = dp.getStatusEntityByName(statusFilter);
            if (s != null)
                qb.setStatus(s);
        }
        if (!organisationFilter.equals(NO_FILTER)) {
            Organisation o = dp.getOrganisationEntityByName(organisationFilter);
            if (o != null)
                qb.setOrganisation(o);
        }
        if (!vendorFilter.equals(NO_FILTER)) {
            Vendor v = dp.getVendorEntityByName(organisationFilter);
            if (v != null);
                //TODO: implement Vendor filter to Task???
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

        if (!descriptionFilter.equals(""))
            qb.setDescription(descriptionFilter);

        return qb.getQuery();
    }

    // Main Logic
    public List<Tab> getTabs() {
        return tabs;
    }
    public String chooseRowColor(Task task) {
        if (task == null || task.getStatus() == null)
            return "null";

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
    public List<Organisation> getOrganizationOptions() {
        return dp.getAllOrganisations();
    }
    public List<ContactPerson> getContactPersonOptions() {
        return dp.getAllContactPersons();
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
    public List<TaskType> getTypeOptions() {
        return dp.getAllTasktypes();
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

            activeTabIndex--;
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
    public void addCreateTab(Task task) {
        // For creation inherited tasks
        tabs.add(new CreateTab(task));
    }
    public void addCorrectTab(Task content) {
        tabs.add(new CorrectTab(content));
    }
    public void addListTab(List<Task> tasks) {
        tabs.add(new ListTab(tasks));
    }
    public void addListTabByFilters() {
        addListTab(dp.getTasks(parseFilters()));
    }
    public Comment getNewActiveTabCommentary() {
        return (tabs.get(activeTabIndex) instanceof Commentable)? ((Commentable) tabs.get(activeTabIndex)).getNewCommentary() : null;
    }
    public void setNewActiveTabCommentary(Comment comment) {
        if (tabs.get(activeTabIndex) instanceof Commentable)
            ((Commentable) tabs.get(activeTabIndex)).setNewCommentary(comment);
    }

    // CRUD buttons
    public void handleFileAttachment(FileUploadEvent event) {
        // TODO: 18.02.16 add logic
    }
    public void createNewTask(Task task) {
        // TODO: 19.02.16 add logic
        try {
            dp.persist(task);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public void updateTask(Task task) {
        try {
            dp.persist(task);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public void giveTaskToCurrentUser(Task task){
        task.setExecutor(user);
        task.setStatus(dp.getStatusEntityByName("выполняется"));

        try {
            dp.persist(task);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public void closeTask(Task task) {
        task.setStatus(dp.getStatusEntityByName("закрыта"));
        updateTask(task);
    }
    public void createChildTask(Task task) {
        addCreateTab(task);
    }
    public void createTaskFromTemplate(Task task) {
        // TODO: 19.02.16 add logic
        throw new NotImplementedException();
    }
    public void addComment(Task task, String content) {
        Comment comment = new Comment();
        comment.setAuthor(user);
        comment.setContent(content);
        comment.setTask(task);
        comment.setWriteDate(new Date());

        try {
            dp.persist(comment);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
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

    // Filter-pattern selection listeners
    public void selectMyTasksPattern(ActionEvent event) {
        //my tasks: executor = user
        addListTab(dp.getTasks(new TasksQueryBuilder().setExecutor(user).getQuery()));
    }
    public void selectFreeTasksPattern(ActionEvent event) {
        //free tasks: executor = null and status = opened
        List<Task> tasks = dp.getTasks(new TasksQueryBuilder().setStatus(dp.getStatusEntityByName("открыта")).getQuery());

        //we can't ask for null values in query, so we have to filter through null executors here
        Iterator<Task> i = tasks.iterator();
        while (i.hasNext())
            if (i.next().getExecutor() != null)
                i.remove();

        addListTab(tasks);
    }
    public void selectClosedTasksPattern(ActionEvent event) {
        //closed tasks: status = closed
        addListTab(dp.getTasks(new TasksQueryBuilder().setStatus(dp.getStatusEntityByName("закрыта")).getQuery()));
    }
    public void selectTrackedTasksPattern(ActionEvent event) {
        throw new NotImplementedException();
    }
    public void selectChangedTasksPattern(ActionEvent event) {
        throw new NotImplementedException();
    }
    public void selectContractsTaskPattern(ActionEvent event) {
        //contracts tasks: taskType = "договор" (contract);
        addListTab(dp.getTasks(new TasksQueryBuilder().setType(dp.getTasktypeEntityByName("договор")).getQuery()));
    }

    // Filter-pattern task counts
    public int getMyCount() {
        return dp.countUserTasks(user);
    }
    public int getOpenCount() {
        int counter = 0;

        Status openStatus = dp.getStatusEntityByName("открыта");

        for (Task t : dp.getAllTasks())
            if (t.getExecutor() == null && openStatus.equals(t.getStatus()))
                counter += 1;

        return counter;
    }
    public int getClosedCount() {
        int counter = 0;

        Status closedStatus = dp.getStatusEntityByName("закрыта");

        for (Task t : dp.getAllTasks())
            if (closedStatus.equals(t.getStatus()))
                counter += 1;

        return counter;
    }
    //TODO: implement patterns when user-logic gets added
    public int getTrackedCount() {
        return 123456;
    }
    public int getChangedCount() {
        return 123456;
    }
    public int getContractsCount() {
        int counter = 0;

        TaskType contract = dp.getTasktypeEntityByName("договор");

        for (Task t : dp.getAllTasks())
            if (contract.equals(t.getType()))
                counter += 1;

        return counter;
    }
}
