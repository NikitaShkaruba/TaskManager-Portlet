package elcom.tabs;

import elcom.Entities.Task;
import java.util.List;

public class ListTab extends Tab {
    public ListTab(List<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public boolean isMoreTab() {
        return false;
    }
    @Override
    public boolean isChangeTab() {
        return false;
    }
    @Override
    public boolean isCreateTab() {
        return false;
    }
    @Override
    public boolean isListTab() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Список задач";
    }
}
