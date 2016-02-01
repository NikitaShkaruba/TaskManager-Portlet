package elcom.main;

import elcom.enums.TaskPriority;
import elcom.enums.TaskStatus;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.faces.bean.ManagedBean;
import java.util.ArrayList;
import java.util.List;

// TODO: 11.01.16 Add logic

// Handles all the information from the databases
// @Local
class DatabaseConnector {
    public DatabaseConnector() {

    }

    public List<Task> getTasks(TaskStatus taskFilter) {
        ArrayList temp = new ArrayList<Task>();
        temp.add(new Task(1, "Give us some water", "Daneeil", TaskStatus.OPEN, TaskPriority.NORMAL));
        temp.add(new Task(2, "Give us food", "Daneeil", TaskStatus.OPEN, TaskPriority.NORMAL));
        temp.add(new Task(3, "Give us place to sleep", "Daneeil", TaskStatus.OPEN, TaskPriority.NORMAL));

        return temp;
    }
    public List<Task> getTasks(TaskStatus taskFilter, String UserName) {
        ArrayList temp = new ArrayList<Task>();
        temp.add(new Task(1, "Give us some water", UserName, TaskStatus.OPEN, TaskPriority.NORMAL));

        return temp;
    }
    public List<String> getEmployees() {
        ArrayList temp = new ArrayList<String>();
        temp.add("Anatoly Pribluda");
        temp.add("Evgeny Tsopa");

        return temp;
    }
    public void addTask(Task task) {
        throw new NotImplementedException();
    }
    public void removeTask(Task task) {
        throw new NotImplementedException();
    }
}
