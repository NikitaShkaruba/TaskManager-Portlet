package elcom.tabs;

import elcom.entities.Task;
import java.util.List;

// Dynamic tabView holds list of tabs which are inherited from this general tab
public abstract class Tab {
    protected List<Task> tasks;

    public abstract String getTitle();
    public List<Task> getTasks() {
        return tasks;
    }
}

