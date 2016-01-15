package elcom;

import javax.faces.bean.ManagedBean;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nikita Shkaruba on 11.01.16.
 *
 * My Contacts:
 * Email: sh.nickita@list.ru
 * GitHub: github.com/SigmaOne
 * Vk: vk.com/wavemeaside
 */

// TODO: 11.01.16 Add logic
public class ListManager {

    public ListManager() {

    }

    public List<Task> getTasks() {
        ArrayList temp = new ArrayList<Task>();
        temp.add(new Task(1, "Give us some water", "Daneeil", TaskStatus.CREATED));
        temp.add(new Task(2, "Give us food", "Daneeil", TaskStatus.CREATED));
        temp.add(new Task(3, "Give us place to sleep", "Daneeil", TaskStatus.CREATED));

        return temp;
    }
}
