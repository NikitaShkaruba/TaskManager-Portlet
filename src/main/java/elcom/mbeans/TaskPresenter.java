package elcom.mbeans;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import com.liferay.portal.kernel.util.WebKeys;
import javax.servlet.http.HttpServletResponse;
import com.liferay.portal.theme.ThemeDisplay;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.StreamedContent;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.event.TabCloseEvent;
import org.primefaces.component.tabview.*;
import com.liferay.portal.util.PortalUtil;
import org.primefaces.model.UploadedFile;
import javax.faces.context.FacesContext;
import javax.annotation.PostConstruct;
import javax.faces.bean.SessionScoped;
import com.liferay.portal.model.User;
import org.apache.commons.io.IOUtils;
import javax.faces.event.ActionEvent;
import javax.portlet.PortletResponse;
import javax.faces.bean.ManagedBean;
import elcom.jpa.TasksQueryBuilder;
import java.nio.charset.Charset;
import elcom.ejbs.DataProvider;
import java.util.ArrayList;
import elcom.entities.*;
import java.util.Date;
import java.util.List;
import elcom.tabs.Tab;
import javax.ejb.EJB;
import elcom.tabs.*;
import java.io.*;

// Handles task CRUD logic, provides proxy interface for dynamic tabView
@ManagedBean(name = "TaskPresenter", eager=true)
@SessionScoped
public class TaskPresenter {
    private Employee currentUser;
    private int activeTabIndex;
    private List<Tab> tabs;

    @EJB
    private DataProvider dp;

    // Task search filters
    private Organisation organisationFilter;
    private String descriptionFilter;
    private Employee executorFilter;
    private Employee creatorFilter;
    private Status statusFilter;
    private Vendor vendorFilter;
    private Group groupFilter;

    public TaskPresenter() {
        statusFilter = null;
        organisationFilter = null;
        vendorFilter = null;
        groupFilter = null;
        executorFilter = null;
        creatorFilter = null;
        descriptionFilter = "";
    }

    // All dependecy injections are unavaiable in constructor, so we need this init() method
    @PostConstruct
    public void init() {
        List<Task> allTasks = dp.getAllTasks();

        currentUser = getCurrentUser();
        tabs = new ArrayList();
        tabs.add(new ListTab(allTasks));
    }

    private TasksQueryBuilder.TasksQuery parseFilters() {
        TasksQueryBuilder qb = new TasksQueryBuilder();

        if (statusFilter != null)
            qb.setStatus(statusFilter);
        if (organisationFilter != null)
            qb.setOrganisation(organisationFilter);
        // Todo: implement Vendor filter to Task???
        if (vendorFilter != null);
        if (groupFilter != null)
            qb.setExecutorGroup(groupFilter);
        if (executorFilter != null)
            qb.setExecutor(executorFilter);
        if (creatorFilter != null)
            qb.setCreator(creatorFilter);
        if (!descriptionFilter.equals(""))
            qb.setDescription(descriptionFilter);

        return qb.getQuery();
    }

    public List<Tab> getTabs() {
        return tabs;
    }
    public String chooseRowColor(Task task) {
        if (task == null || task.getStatus() == null)
            return "null";

        if (task.getStatus().getName().equals("открыта")) {
            return "Red";
        } else if (task.getStatus().getName().equals("закрыта")) {
            return "Green";
        } else if (task.getStatus().getName().equals("отменена")) {
            return "Green";
        } else if (task.getStatus().getName().equals("отложена")) {
            return "Blue";
        } else if (task.getStatus().getName().equals("шаблон")) {
            return "Black";
        } else if (task.getStatus().getName().equals("выполнена")) {
            return "Green";
        } else if (task.getStatus().getName().equals("выполняется")) {
            return "Brown";
        } else {
            return null;
        }
    }

    public List<Comment> getTaskComments(Task task) {
        return dp.getTaskComments(task);
    }
    public List<TaskFile> getTaskFiles(Task task) {
        return dp.getTaskFiles(task);
    }
    public Status getStatusFilter() {
        return statusFilter;
    }
    public Organisation getOrganisationFilter() {
        return organisationFilter;
    }
    public Vendor getVendorFilter() {
        return vendorFilter;
    }
    public Group getGroupFilter() {
        return groupFilter;
    }
    public Employee getExecutorFilter() {
        return executorFilter;
    }
    public Employee getCreatorFilter() {
        return creatorFilter;
    }
    public String getDescriptionFilter() {
        return descriptionFilter;
    }

    public void setStatusFilter(Status filter) {
        this.statusFilter = filter;
    }
    public void setOrganisationFilter(Organisation organisationFilter) {
        this.organisationFilter = organisationFilter;
    }
    public void setVendorFilter(Vendor vendorFilter) {
        this.vendorFilter = vendorFilter;
    }
    public void setGroupFilter(Group groupFilter) {
        this.groupFilter = groupFilter;
    }
    public void setExecutorFilter(Employee executorFilter) {
        this.executorFilter = executorFilter;
    }
    public void setCreatorFilter(Employee creatorFilter) {
        this.creatorFilter = creatorFilter;
    }
    public void setDescriptionFilter(String descriptionFilter) {
        this.descriptionFilter = descriptionFilter;
    }

    // Data for filter drop-downs
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

    // Dynamic tabView tab logic
    public void onTabClose(TabCloseEvent event) {
        if (tabs.size() == 1) {
            tabs.remove(0);
            // Active tab index is still 0, because it's feature - it's part of dynamic tabView.
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
    public void addCorrectTab(Task content) {
        tabs.add(new CorrectTab(content));
    }
    public void addListTab(List<Task> tasks) {
        tabs.add(new ListTab(tasks));
    }
    public void addListTabByFilters() {
        addListTab(dp.getTasks(parseFilters()));
    }

    // Dynamic tabview proxy logic
    public String getNewActiveTabCommentary() {
        return (tabs.get(activeTabIndex) instanceof Commentable)? ((Commentable) tabs.get(activeTabIndex)).getNewCommentary().getContent() : "";
    }
    public void setNewActiveTabCommentary(String content) {

        if (tabs.get(activeTabIndex) instanceof Commentable) {
            ((Commentable) tabs.get(activeTabIndex)).getNewCommentary().setContent(content);
        }
    }

    // CRUD task operations
    public void createNewTask(Task task) {
        // Fill missing task properties
        Date currentDate = new Date();
        // task.setCreator(); Todo: take tis construction out to isolated method
        for (wfuser w : dp.getAllWfusers()) {
            if (currentUser.equals(w.employee)) {
                task.setWfCreator(w);
                break;
            }
        }
        // task.setExecutor();
        for (wfuser w : dp.getAllWfusers()) {
            if (task.getExecutor().equals(w.employee)) {
                task.setWfExecutor(w);
                break;
            }
        }
        task.setCreationDate(currentDate);
        task.setModificationDate(currentDate);

        try {
            dp.persist(task);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public void updateTask(Task task) {
        // Fill missing task properties
        for (wfuser w : dp.getAllWfusers()) {
            if (task.getExecutor().equals(w.employee)) {
                task.setWfExecutor(w);
                break;
            }
        }
        task.setModificationDate(new Date());

        try {
            dp.persist(task);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public void giveTaskToCurrentUser(Task task){
        for (wfuser w : dp.getAllWfusers()) {
            if (currentUser.equals(w.employee)) {
                task.setWfExecutor(w);
                break;
            }
        }
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
    public void addComment(Task task, String content) {
        Comment comment = new Comment();

        for (wfuser w : dp.getAllWfusers()) {
            if (currentUser.equals(w.employee)) {
                comment.setWfAuthor(w);
                break;
            }
        }

        comment.setAuthor(currentUser);
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
        // Plug to deny tabView without tabs
        if (activeTabIndex < 0)
            activeTabIndex = 0;

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
    public void attachNewFile(FileUploadEvent event) throws IOException {
        UploadedFile file = event.getFile();

        TaskFile taskFile = new TaskFile();
        taskFile.setTask(tabs.get(activeTabIndex).getTasks().get(0));
        taskFile.setName(file.getFileName());
        taskFile.setSize(file.getSize());
        taskFile.setType(file.getContentType());
        taskFile.setCreationDate(new Date());

        // Convert bytes to string
        InputStream inputStream = file.getInputstream();
        StringWriter writer = new StringWriter();
        IOUtils.copy(inputStream, writer, "UTF-8");
        String theString = writer.toString();
        taskFile.setBytes(theString);

        dp.persist(taskFile);
    }
    public StreamedContent downloadFile(TaskFile file) throws IOException {
        // Make response
        PortletResponse portletResponse = (PortletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        HttpServletResponse res = PortalUtil.getHttpServletResponse(portletResponse);
        res.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");
        res.setHeader("Content-Transfer-Encoding", "binary");
        res.setContentType("application/octet-stream");
        res.flushBuffer();
        OutputStream out = res.getOutputStream();
        out.write(file.getBytes().getBytes(Charset.forName("UTF-8")));
        out.close();

        return null;
    }

    // Predefined task tabs
    public void selectMyTasksPattern(ActionEvent event) {
        List<Task> tasks = dp.getTasks(new TasksQueryBuilder().setExecutor(currentUser).getQuery());
        addListTab(tasks);
    }
    public void selectFreeTasksPattern(ActionEvent event) {
        List<Task> tasks = dp.getTasks(new TasksQueryBuilder().setStatus(dp.getStatusEntityByName("открыта")).getQuery());
        addListTab(tasks);
    }
    public void selectClosedTasksPattern(ActionEvent event) {
        List<Task> tasks = dp.getTasks(new TasksQueryBuilder().setStatus(dp.getStatusEntityByName("закрыта")).getQuery());
        addListTab(tasks);
    }
    public void selectTrackedTasksPattern(ActionEvent event) {
        throw new NotImplementedException();
    }
    public void selectChangedTasksPattern(ActionEvent event) {
        throw new NotImplementedException();
    }
    public void selectContractsTaskPattern(ActionEvent event) {
        List<Task> tasks = dp.getTasks(new TasksQueryBuilder().setType(dp.getTasktypeEntityByName("договор")).getQuery());
        addListTab(tasks);
    }

    // Predefined tasks counters
    public int getMyTasksAmount() {
        return dp.countUserTasks(currentUser);
    }
    public int getOpenTasksAmount() {
        return dp.countFreeTasks();
    }
    public int getClosedTasksAmount() {
        return dp.countClosedTasks();
    }
    public int getContractsAmount() {
        return dp.countContractTasks();
    }

    public Employee getCurrentUser() {
        // Liferay User
        User u = ((ThemeDisplay) FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get(WebKeys.THEME_DISPLAY)).getUser();

        return dp.getEmployeeEntityByName(u.getFullName());
    }
}
