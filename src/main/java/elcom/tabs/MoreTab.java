package elcom.tabs;

import elcom.entities.Task;
import java.util.ArrayList;

public class MoreTab extends Tab implements TaskSelector {
    private Task selectedTask;

    public MoreTab(Task task) {
        this.tasks = new ArrayList();

        tasks.add(task);
        selectedTask = task;
    }

    @Override
    public String getTitle() {
        return "Заявка №" + tasks.get(0).getId();
    }
    @Override
    public Task getSelectedTask() {
        return selectedTask;
    }

    @Override
    public void setSelectedTask(Task selectedTask) {
        this.selectedTask = selectedTask;
    }
}