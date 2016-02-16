package elcom.tabs;

import elcom.entities.Task;
import org.primefaces.event.SelectEvent;

import java.util.List;

public class ListTab extends Tab {
    public ListTab(List<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public String getTitle() {
        return "Список задач";
    }
}
