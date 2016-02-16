package elcom.tabs;

import elcom.entities.Task;

import java.util.ArrayList;

public class CreateTab extends Tab {
    public CreateTab() {
        this.tasks = new ArrayList();

        tasks.add(new Task());
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
        return true;
    }
    @Override
    public boolean isListTab() {
        return false;
    }

    @Override
    public String getTitle() {
        return "Новая Заявка";
    }
}
