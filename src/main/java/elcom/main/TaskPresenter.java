package elcom.main;

import javax.faces.bean.SessionScoped;
import javax.faces.bean.ManagedBean;
import java.util.*;

// TODO: 04.02.16 Consider assigning some responsibilities to a bunch of support classes 

// This bean handles logic from ViewTasks page
@ManagedBean(name = "TaskPresenter", eager=true)
@SessionScoped
public class TaskPresenter {
    DatabaseConnector databaseConnector = new DatabaseConnector(); // ejb
    private List<Task> tasks;
    private String selectedTaskFilter;
    private String selectedEmployeeFilter;
    private String currentUser;
    private int currentPage;
    private int displayedAmount;

    public TaskPresenter() {
        currentUser = "Andrey Pribluda";
        selectedTaskFilter = "Любой";
        selectedEmployeeFilter = "Мои";
        currentPage = 1;
        displayedAmount = 15;
        
        tasks = databaseConnector.getTasks(this.selectedTaskFilter, currentUser);
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
    public String getTip() {
        return "Ваш запрос был успешно выполнен.";
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
    public String getCurrentUser() {
        return currentUser;
    }
    public int getCurrentPage() {
        return currentPage;
    }
    public int getDisplayedAmount() {
        return displayedAmount;
    }

    // Setters
    public void setSelectedTaskFilter(String filter) {
        selectedTaskFilter = filter;
    }
    public void setSelectedEmployeeFilter(String filter) {
        selectedEmployeeFilter = filter;
    }
    public void setCurrentUser(String username) {
        this.currentUser = username;
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

    // ListsFor GUI MenuOptions
    public List<String> getEmployeeFilterOptions() {
        List<String> options = new ArrayList<>();

        options.add("Мои");
        options.add("Все");

        return options;
    }
    public List<String> getTaskStatusesOptions() {
        List<String> options = new ArrayList<>();

        options.add("Любой");
        options.add("Выполнена");
        options.add("Выполняется");
        options.add("Открыта");
        options.add("Закрыта");
        options.add("Отложена");
        options.add("Шаблон");
        options.add("Отменена");

        return options;
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
        tasks = databaseConnector.getTasks(selectedTaskFilter, selectedEmployeeFilter);
    }
    public void selectNewEmployeeFilter() {
        tasks = databaseConnector.getTasks(selectedTaskFilter, selectedEmployeeFilter);
    }
}
