package elcom.mbeans;

import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.User;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import elcom.entities.*;
import elcom.ejbs.DataProvider;
import elcom.jpa.TasksQueryBuilder;
import elcom.tabs.*;
import elcom.tabs.Tab;
import org.apache.commons.io.IOUtils;
import org.primefaces.component.tabview.*;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.event.TabCloseEvent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ManagedBean;
import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.portlet.PortletResponse;
import javax.servlet.http.HttpServletResponse;


// This MBean handles selection table selection logic, provides menu item options
@ManagedBean(name = "TaskPresenter", eager=true)
@SessionScoped
public class TaskPresenter {
    private Employee user;
    private List<Tab> tabs;
    private int activeTabIndex;

    @EJB
    private DataProvider dp;

    private Status statusFilter;
    private Organisation organisationFilter;
    private Vendor vendorFilter;
    private Group groupFilter;
    private Employee executorFilter;
    private Employee creatorFilter;
    private String descriptionFilter;

    public TaskPresenter() {
        statusFilter = null;
        organisationFilter = null;
        vendorFilter = null;
        groupFilter = null;
        executorFilter = null;
        creatorFilter = null;
        descriptionFilter = "";
    }
    // Cannot move tasks initialization to a constructor coz ejb injections occurs after constructor
    @PostConstruct
    public void init() {
        List<Task> firstHundredTasks = dp.getAllTasks();

        user = getCurrentUser();
        tabs = new ArrayList();
        tabs.add(new ListTab(firstHundredTasks));
    }

    private TasksQueryBuilder.TasksQuery parseFilters() {
        TasksQueryBuilder qb = new TasksQueryBuilder();

        if (statusFilter != null)
            qb.setStatus(statusFilter);
        if (organisationFilter != null)
            qb.setOrganisation(organisationFilter);
        //TODO: implement Vendor filter to Task???
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

    // Main Logic
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
    public boolean isNewToUser(Task task) {
        // TODO: 13.02.16 add liferay-bounded logic
        return true;
    }

    // Getters
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

    // Setters
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
    public String getNewActiveTabCommentary() {
        return (tabs.get(activeTabIndex) instanceof Commentable)? ((Commentable) tabs.get(activeTabIndex)).getNewCommentary().getContent() : null;
    }
    public void setNewActiveTabCommentary(String content) {
        if (tabs.get(activeTabIndex) instanceof Commentable) {
            addComment(tabs.get(activeTabIndex).getTasks().get(0), content);
            ((Commentable) tabs.get(activeTabIndex)).setNewCommentary(new Comment());
        }
    }

    // CRUD buttons
    public void createNewTask(Task task) {
        // Fill missing properties
        Date currentDate = new Date();

        // task.setCreator();
        for (wfuser w : dp.getAllWfusers()) {
            if (user.equals(w.employee)) {
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
            if (user.equals(w.employee)) {
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
    public void attachNewFile(FileUploadEvent event) throws IOException {
        UploadedFile file = event.getFile();

        TaskFile taskFile = new TaskFile();
        taskFile.setTask(tabs.get(activeTabIndex).getTasks().get(0));
        taskFile.setName(file.getFileName());
        taskFile.setSize(file.getSize());
        taskFile.setType(file.getContentType());
        taskFile.setCreationDate(new Date());

        InputStream inputStream = file.getInputstream();
        StringWriter writer = new StringWriter();
        IOUtils.copy(inputStream, writer, "UTF-8");
        String theString = writer.toString();
        taskFile.setBytes(theString);

        // For debug
        descriptionFilter = theString;
        dp.persist(taskFile);
    }
    public StreamedContent downloadFile(TaskFile file) throws IOException {
        PortletResponse portletResponse = (PortletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        HttpServletResponse res = PortalUtil.getHttpServletResponse(portletResponse);
        res.setHeader("Content-Disposition", "attachment; filename=\"file.txt\"");
        res.setHeader("Content-Transfer-Encoding", "binary");
        res.setContentType("application/octet-stream");
        res.flushBuffer();

        OutputStream out = res.getOutputStream();
        out.write(file.getBytes().getBytes(Charset.forName("UTF-8")));
        out.close();

        return null;
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
    public void selectMyTasksPattern(ActionEvent event) {
        // my tasks: executor = user
        addListTab(dp.getTasks(new TasksQueryBuilder().setExecutor(user).getQuery()));
    }
    public void selectFreeTasksPattern(ActionEvent event) {
        // free tasks: executor = null and status = opened
        List<Task> tasks = dp.getTasks(new TasksQueryBuilder().setStatus(dp.getStatusEntityByName("открыта")).getQuery());

        // we can't ask for null values in query, so we have to filter through null executors here
        //Iterator<Task> i = tasks.iterator();
        //while (i.hasNext())
        //    if (i.next().getExecutor() != null)
        //        i.remove();

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
        return dp.countFreeTasks();
    }
    public int getClosedCount() {
        return dp.countClosedTasks();
    }
    public int getContractsCount() {
        return dp.countContractTasks();
    }

    public Employee getCurrentUser() {
        // Liferay User
        User u = ((ThemeDisplay) FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get(WebKeys.THEME_DISPLAY)).getUser();

        return dp.getEmployeeEntityByName(u.getFullName());
    }
}
