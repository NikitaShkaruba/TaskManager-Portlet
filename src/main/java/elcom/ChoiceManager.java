package elcom;

import javax.faces.bean.ManagedBean;

/**
 * Created by Nikita Shkaruba on 11.01.16.
 *
 * My Contacts:
 * Email: sh.nickita@list.ru
 * GitHub: github.com/SigmaOne
 * Vk: vk.com/wavemeaside
 */

// TODO: 11.01.16 Add class logic
@ManagedBean(name = "ChoiceManager")
public class ChoiceManager {
    private short currentPageNumber = 1;
    private short itemsOnPage = 15;
    private TaskFilter taskFilter = TaskFilter.ALL;
    private EmployeeFilter employeeFilter = EmployeeFilter.MINE;

    public ChoiceManager() {

    }

    public short getCurrentPage() {
        return currentPageNumber;
    }
}

enum TaskFilter{
    ALL,
    FREE,
    CREATED,
    CLOSED
}
enum EmployeeFilter {
    ALL,
    MINE
}