package elcom.tabs;

import elcom.entities.Task;
import java.util.List;

public abstract class Tab {
    protected List<Task> tasks;

    public abstract boolean isMoreTab();
    public abstract boolean isChangeTab();
    public abstract boolean isCreateTab();
    public abstract boolean isListTab();

    public abstract String getTitle();
    public List<Task> getTasks() {
        return tasks;
    }
}

