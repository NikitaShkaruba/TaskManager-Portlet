package elcom.ejbs;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;

import elcom.Entities.Task;
import elcom.MBeans.TaskPresenter;
import org.junit.Before;
import java.util.List;
import org.junit.Test;

public class TaskPresenterTest {
    TaskPresenter taskPresenter = new TaskPresenter();
    final int tasksTotalSize = 200;

    @Before
    public void plugTasks() {
        List<Task> taskPlug = new ArrayList<>();

        for(int i = 0; i < tasksTotalSize; i++)
            taskPlug.add(new Task());

        taskPresenter.setTasks(taskPlug);
    }

    // Average case means (tasksTotalSize/maxTasksShowed != n), n is an integer
    @Test
    public void showedTasksAverageCaseTest() {
        taskPresenter.setDisplayedAmount(15);
        taskPresenter.setCurrentPage(1);

        for (int i = 1; i <= taskPresenter.getPagesCount(); i++, taskPresenter.setCurrentPage(i)) {
            if (i != taskPresenter.getPagesCount())
                assertEquals("Average case fails", taskPresenter.getShowedTasks().size(), taskPresenter.getDisplayedAmount());
            else
                assertEquals("Special case fails", taskPresenter.getShowedTasks().size(), tasksTotalSize - (i-1)*taskPresenter.getDisplayedAmount());
        }
    }

    // Perfect case means (tasksTotalSize/maxTasksShowed = n), n is an integer
    @Test
    public void showedTasksPerfectCaseTest() {
        taskPresenter.setDisplayedAmount(100);
        taskPresenter.setCurrentPage(1);

        for (int i = 1; i <= taskPresenter.getPagesCount(); i++, taskPresenter.setCurrentPage(i)) {
            if (i != taskPresenter.getPagesCount())
                assertEquals("Average case fails", taskPresenter.getShowedTasks().size(), taskPresenter.getDisplayedAmount());
        }
    }
}