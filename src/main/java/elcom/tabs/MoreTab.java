package elcom.tabs;

import elcom.entities.Task;

import java.util.ArrayList;

public class MoreTab extends Tab {
    public MoreTab(Task task) {
        this.tasks = new ArrayList();

        tasks.add(task);
    }

    @Override
    public String getTitle() {
        return "Заявка №" + tasks.get(0).getId();
    }
}
