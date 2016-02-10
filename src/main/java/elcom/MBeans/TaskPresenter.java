package elcom.MBeans;

import elcom.Entities.Task;
import elcom.ejbs.IDatabaseConnectorLocal;
import elcom.enums.TaskData;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ManagedBean;
import java.util.ArrayList;
import java.util.List;

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
    @EJB
    private IDatabaseConnectorLocal dbc;

    public TaskPresenter() {
        selectedTaskFilter = "Любой";
        selectedEmployeeFilter = "Все";
        currentPage = 1;
        displayedAmount = 15;
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
    public int getSelectedTaskId() {
        return 100500;
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

    // Setters
    public void setSelectedTaskFilter(String filter) {
        this.selectedTaskFilter = filter;
    }
    public void setSelectedEmployeeFilter(String filter) {
        this.selectedEmployeeFilter = filter;
    }
    public void setCurrentPage(int pageNumber) {
        this.currentPage = pageNumber;
    }
    public void setDisplayedAmount(int amount) {
        this.displayedAmount = amount;
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
        return dbc.readData(TaskData.STATUS);
    }
    public List<Integer> getItemAmountOptions() {
        List<Integer> variants = new ArrayList<>();

        for(int i = 5; i <= 30; i += 5)
            variants.add(i);

        return variants;
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
    }
    public void selectNewEmployeeFilter() {
        tasks = dbc.readTasks(selectedTaskFilter, selectedEmployeeFilter);
    }
    public void setNextPage() {
        if (currentPage != getPagesCount())
            currentPage++;
    }
    public void setPreviousPage() {
        if (currentPage != 1)
            currentPage--;
    }
    public void setLastPage() {
        currentPage = getPagesCount();
    }
    public void setFirstPage() {
        currentPage = 1;
    }
    public void delete() {
        throw new NotImplementedException();
    }
}
