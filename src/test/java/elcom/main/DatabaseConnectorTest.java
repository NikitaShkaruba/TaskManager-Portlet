package elcom.main;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import elcom.enums.TaskPriority;
import elcom.enums.TaskStatus;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import java.util.List;
/**
 * Created by Nikita Shkaruba on 16.01.16.
 * <p>
 * My Contacts:
 * Email: sh.nickita@list.ru
 * GitHub: github.com/SigmaOne
 * Vk: vk.com/wavemeaside
 */

public class DatabaseConnectorTest {
    DatabaseConnector databaseConnector = new DatabaseConnector();
    private ArrayList<Task> verifiedTasks = new ArrayList<Task>();

    @Before
    public void initialize() {
        // Verified tasks can be changed :\
        // Also, index is always 0 and priority is always normal because it is not used in comparison
        verifiedTasks.add(new Task(0, "[cnt]/35-12/работы по переносу продакшн с старого жееза на железо из 33-12", "Andrei Pribluda", TaskStatus.PROCESSING, TaskPriority.NORMAL));
        verifiedTasks.add(new Task(0, "Оплата : ES-345 RedCenter // 10.09-14.09 // 5 дн. // 5 человек", "Serge Klimenkov", TaskStatus.OPEN, TaskPriority.NORMAL));
        verifiedTasks.add(new Task(0, "Управление производительностью FreeBSD", "Dmitry Afanasiev", TaskStatus.OPEN, TaskPriority.NORMAL));
        verifiedTasks.add(new Task(0, "[cnt]/30-10/Поставка \"Гловис Рус\"", "Anton V Gavrilov", TaskStatus.TEMPLATE, TaskPriority.NORMAL));
        verifiedTasks.add(new Task(0, "запрос x4100", "Alexey Guljaev", TaskStatus.COMPLETED, TaskPriority.NORMAL));
    }

    @Test
    public void testGetAllTasks() {
        // Retrieve all the tasks from db and try to find every verified task
        List<Task> tasks = databaseConnector.getTasks(TaskStatus.OPEN);
        assertTrue("No tasks retrieved", tasks.size() != 0);

        for(Task task: tasks)
            verifiedTasks.removeIf(p -> p.equals(task));
        assertEquals("Not all verified tasks found.", 0, verifiedTasks.size());
    }

    @Test
    public void testGetUserTasks() {
        int sizeBefore = verifiedTasks.size();

        for(Task task: databaseConnector.getTasks(TaskStatus.OPEN, "Dmitry Afanasiev"))
            verifiedTasks.removeIf(p -> p.equals(task));
        assertEquals("Can't find Dmitry Afanasiev's tasks", verifiedTasks.size(), sizeBefore-1);

        sizeBefore = verifiedTasks.size();
        for(Task task: databaseConnector.getTasks(TaskStatus.OPEN, "Andrei Pribluda"))
            verifiedTasks.removeIf(p -> p.equals(task));
        assertEquals("Can't find Andrei Pribluda's tasks", verifiedTasks.size(), sizeBefore-1);

        sizeBefore = verifiedTasks.size();
        for(Task task: databaseConnector.getTasks(TaskStatus.OPEN, "Vasya Pupkin"))
            verifiedTasks.removeIf(p -> p.equals(task));
        assertEquals("Found nonexistent task", verifiedTasks.size(), sizeBefore);
    }

    @Test
    public void testAddRemoveTask() {
        Task testTask = new Task(0, "Add task test", "Test Testov", TaskStatus.CANCELED, TaskPriority.VERYLOW);

        databaseConnector.addTask(testTask);
        List<Task> tasks = databaseConnector.getTasks(TaskStatus.CANCELED, "Test Testov");
        assertTrue("Task was not added", tasks.size() != 0);

        databaseConnector.removeTask(testTask);
        tasks = databaseConnector.getTasks(TaskStatus.CANCELED, "Test Testov");
        assertTrue("Task was not removed", tasks.size() == 0);
    }
}