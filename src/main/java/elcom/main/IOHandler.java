package elcom.main;

import elcom.enums.TaskStatus;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.faces.bean.SessionScoped;
import javax.faces.bean.ManagedBean;
import java.util.*;

/**2
 * Created by Nikita Shkaruba on 11.01.16.
 * <p>
 * My Contacts:
 * Email: sh.nickita@list.ru
 * GitHub: github.com/SigmaOne
 * Vk: vk.com/wavemeaside
 */

// TODO: 12.01.16 Rethink about general manager value, maybe 3 managers could do the job without him :\
// TODO: 12.01.16 Find a way to return List instead of map in "*Variants" methods
// TODO: 11.01.16 Read about this annotation deeply
// TODO: 11.01.16 Add class logic

// Handles all the input\output from user
@ManagedBean(name = "IOHandler")
@SessionScoped
class IOHandler {
    DatabaseConnector databaseConnector = new DatabaseConnector();
    private TaskStatus currentTaskFilter = TaskStatus.TEMPLATE;
    private boolean isAllTasks = true;
    private short currentPage = 1;
    private short tasksShowed = 15;
    private String currentUser = "Andrey Pribluda";
    private List<Task> tasks;

    public IOHandler() {

    }

    public void setCurrentPage() {
        //logic
        throw new NotImplementedException();
    }
    public String getTip() {
        return "Ваш запрос был успешно выполнен.";
    }
    public String getCurrentTaskFilter() {
        return currentTaskFilter + "";
    }
    public boolean getCurrentEmployeeFilter() {
        return  isAllTasks;
    }
    public String getCurrentPage() {
        return currentPage + "";
    }
    public String getItemAmount() {
        return tasksShowed + "";
    }
    public List<Task> getTasks() {
        return databaseConnector.getTasks(this.currentTaskFilter);
    }
    public List<Task> getUserTasks(String username) {
        return databaseConnector.getTasks(this.currentTaskFilter, username);
    }
    public String getMaxPageIndex() {
        return tasks.size()/tasksShowed + "";
    }

    // TODO: 12.01.16 Refactor this awkward Map initialization
    public Map<String, String> getItemAmountVariants() {
        HashMap<String, String> temp = new HashMap<String, String>();
        temp.put("15", "15");
        temp.put("30", "30");
        temp.put("45", "45");

        return temp;
    }
    public Map<String, String> getPageVariants() {
        HashMap<String, String> temp = new HashMap<String, String>();
        temp.put("1", "1");
        temp.put("5", "5");
        temp.put("10", "10");

        return temp;
    }

    public void addTask() {

    }
}

