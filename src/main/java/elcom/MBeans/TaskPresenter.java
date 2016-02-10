package elcom.MBeans;

import elcom.Entities.Task;
import elcom.ejbs.IDatabaseConnectorLocal;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ManagedBean;
import java.util.*;

// TODO: 06.02.16 Make nextPage/PrevPage buttons hidden when necessary

// This bean handles logic from ViewTasks page
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

    // Getters
    public String getSelectedTaskFilter() {
        return selectedTaskFilter;
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
}
