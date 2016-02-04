package elcom.main;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.List;

// TODO: 11.01.16 Add logic

// Handles all the information retrieving from Elcom databases
// @Local
class DatabaseConnector {
    public DatabaseConnector() {

    }

    public List<Task> getTasks(String taskFilter, String userFilter) {
        ArrayList temp = new ArrayList<Task>();

        // TODO: 03.02.16 add taskFilter logic
        if (userFilter.equals("Все")) {
            temp.add(new Task(1, "Give us some water", "Daneeil", "Открыта", "Обычный"));
            temp.add(new Task(2, "Give us food", "Daneeil", "Открыта", "Обычный"));
            temp.add(new Task(3, "Give us place to sleep", "Daneeil", "Открыта", "Обычный"));
        } else
            temp.add(new Task(1, "Give us some water", userFilter, "Открыта", "Обычный"));

        for (int i = 0; i < 200; i++)
            temp.add(new Task(i, "Plug", "Plug", "Plug", "Plug"));

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
