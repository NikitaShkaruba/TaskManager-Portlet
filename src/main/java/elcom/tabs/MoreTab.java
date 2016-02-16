package elcom.tabs;

import elcom.entities.Task;

import java.util.ArrayList;

public class MoreTab extends Tab {
    public MoreTab(Task task) {
        this.tasks = new ArrayList();

        tasks.add(task);
    }

    @Override
    public boolean isMoreTab() {
        return true;
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
        return false;
    }

    @Override
    public String getTitle() {
        return "Заявка №" + tasks.get(0).getId();
    }
}
