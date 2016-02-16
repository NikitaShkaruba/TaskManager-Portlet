package elcom.tabs;

import elcom.entities.Task;

import java.util.ArrayList;

public class CreateTab extends Tab {
    public CreateTab() {
        this.tasks = new ArrayList();

        tasks.add(new Task());
    }

    @Override
    public String getTitle() {
        return "Новая Заявка";
    }
}
