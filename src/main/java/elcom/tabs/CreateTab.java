package elcom.tabs;

import elcom.entities.Task;
import java.util.ArrayList;

public class CreateTab extends Tab {
    public CreateTab() {
        this.tasks = new ArrayList();

        tasks.add(new Task());
    }
    public CreateTab(Task parent) {
        this.tasks = new ArrayList();

        Task child= new Task();
        child.setParentTask(parent);
        tasks.add(child);
    }

    @Override
    public String getTitle() {
        return "Новая Заявка";
    }
}
