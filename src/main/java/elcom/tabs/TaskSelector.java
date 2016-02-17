package elcom.tabs;

import elcom.entities.Task;

// Interface provides proxy logic for TaskPresenter
public interface TaskSelector {
    Task getSelectedTask();
    void setSelectedTask(Task selectedTask);
}
