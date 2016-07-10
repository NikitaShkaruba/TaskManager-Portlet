package elcom.tabs;

import elcom.entities.Task;
import java.util.ArrayList;

// Tab for creating tasks
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
