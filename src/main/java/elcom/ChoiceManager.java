package elcom;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by Nikita Shkaruba on 11.01.16.
 *
 * My Contacts:
 * Email: sh.nickita@list.ru
 * GitHub: github.com/SigmaOne
 * Vk: vk.com/wavemeaside
 */

// TODO: 11.01.16 Add class logic
public class ChoiceManager {
    private short currentPage = 1;
    private short tasksShowed = 15;
    private TaskStatus taskFilter = TaskStatus.ALL;
    private EmployeeFilter employeeFilter = EmployeeFilter.MINE;

    public ChoiceManager() {

    }

    public TaskStatus getTaskStatus() {
        return taskFilter;
    }
    public EmployeeFilter getEmployeeFilter() {
        return employeeFilter;
    }
    public short getTasksShowed() {
        return tasksShowed;
    }
    public short getCurrentPage() {
        return currentPage;
    }
    
    public void setCurrentPage() {
        //logic
        throw new NotImplementedException();
    }
}

enum TaskStatus {
    ALL,
    FREE,
    CREATED,
    CLOSED
}
enum EmployeeFilter {
    ALL,
    MINE
}