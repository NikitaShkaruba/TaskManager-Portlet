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
import java.util.TreeSet;
import java.util.List;
import java.util.Set;
import javax.ejb.EJB;

// This MBean handles logic from ViewTasks page
@ManagedBean(name = "TaskPresenter", eager=true)
@SessionScoped
public class TaskPresenter {
    private List<Task> tasks;
    private String selectedTaskFilter;
    private String selectedEmployeeFilter;
    private int currentPage;
    private int displayedAmount;

    private boolean firstPageDisabled;
    private boolean lastPageDisabled;
    private boolean prevPageDisabled;
    private boolean nextPageDisabled;

    @EJB
    private IDatabaseConnectorLocal dbc;
    private Task selectedTask;

    public TaskPresenter() {
        selectedTaskFilter = "Любой";
        selectedEmployeeFilter = "Все";
        currentPage = 1;
        displayedAmount = 15;

        firstPageDisabled = true;
        lastPageDisabled = false;
        prevPageDisabled = true;
        nextPageDisabled = false;
    }
    // Cannot move tasks initialization to a constructor coz ejb injections occurs after constructor
    @PostConstruct
    public void init() {
        tasks = dbc.readTasks(this.selectedTaskFilter, this.selectedEmployeeFilter);
    }

    // Main Logic
    public List<Task> getShowedTasks() {
        int beginIndex = (currentPage - 1) * displayedAmount;
        int endIndex;

        if (currentPage != getPagesCount())
            endIndex = beginIndex + displayedAmount;
        else
            endIndex = tasks.size();

        return tasks.subList(beginIndex, endIndex);
    }
    public int getPagesCount() {
        return (int)Math.ceil((double)tasks.size() / displayedAmount);
    }
    public int getTasksAmount() {
        return tasks.size();
    }
    private long getSelectedTaskId() {
        return selectedTask.getId();
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
    public int getCurrentPage() {
        return currentPage;
    }
    public int getDisplayedAmount() {
        return displayedAmount;
    }
    public boolean isFirstPageDisabled() {
        return firstPageDisabled;
    }
    public boolean isLastPageDisabled() {
        return lastPageDisabled;
    }
    public boolean isPrevPageDisabled() {
        return prevPageDisabled;
    }
    public boolean isNextPageDisabled() {
        return nextPageDisabled;
    }

    // Setters
    public void setSelectedTaskFilter(String filter) {
        this.selectedTaskFilter = filter;
    }
    public void setSelectedEmployeeFilter(String filter) {
        this.selectedEmployeeFilter = filter;
    }
    public void setCurrentPage(int pageNumber) {
        this.currentPage = pageNumber;
        updateButtonsStatuses();
    }
    public void setDisplayedAmount(int amount) {
        this.displayedAmount = amount;
        setCurrentPage(1);
    }
    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
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
    public List<Integer> getItemAmountOptions() {
        Set<Integer> options = new TreeSet<Integer>();

        int lowerBound = tasks.size() < 5 ? tasks.size() : 5;
        int upperBound = tasks.size() < 30 ? tasks.size() : 30;
        int cursor = lowerBound;

        options.add(lowerBound);

        while ((cursor % 5) != 0)
            cursor += 1;
        while (cursor < upperBound) {
            options.add(cursor);
            cursor += 5;
        }

        options.add(upperBound);

        if (!(options.contains(displayedAmount)))
            displayedAmount = upperBound;

        return new ArrayList<Integer>(options);
    }
    public List<Integer> getPagesOptions() {
        List<Integer> variants = new ArrayList<>();

        for(int i = 1; i <= getPagesCount(); i++)
            variants.add(i);

        return variants;
    }

    // AJAX Listeners
    public void selectNewTaskFilter() {
        tasks = dbc.readTasks(selectedTaskFilter, selectedEmployeeFilter);
        setCurrentPage(1);
    }
    public void selectNewEmployeeFilter() {
        tasks = dbc.readTasks(selectedTaskFilter, selectedEmployeeFilter);
        setCurrentPage(1);
    }
    public void setNextPage() {
        if (currentPage != getPagesCount())
            setCurrentPage(currentPage + 1);
    }
    public void setPreviousPage() {
        if (currentPage != 1)
            setCurrentPage(currentPage - 1);
    }
    public void setLastPage() {
        setCurrentPage(getPagesCount());
    }
    public void setFirstPage() {
        setCurrentPage(1);
    }

    public void updateButtonsStatuses() {
        if (currentPage == 1) {
            firstPageDisabled = true;
            prevPageDisabled = true;
        }
        else {
            firstPageDisabled = false;
            prevPageDisabled = false;
        }

        if (currentPage == getPagesCount()) {
            lastPageDisabled = true;
            nextPageDisabled = true;
        }
        else {
            lastPageDisabled = false;
            nextPageDisabled = false;
        }

        if (tasks.size() == 0) {
            firstPageDisabled = true;
            prevPageDisabled = true;
            lastPageDisabled = true;
            nextPageDisabled = true;
        }
    }
    public void onRowSelect(SelectEvent event) {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("CorrectTask.xhtml?id=" + getSelectedTaskId());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
