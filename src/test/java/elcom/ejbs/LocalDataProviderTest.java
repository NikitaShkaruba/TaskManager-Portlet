package elcom.ejbs;

import static org.junit.Assert.assertTrue;

import elcom.entities.*;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LocalDataProviderTest {
    LocalDataProvider dp;
    @Before
    public void SetupDataProvider() {
        dp = new LocalDataProvider();
    }

    //Untested — getTaskComments(), instantiateTaskByTemplate

    @Test
    public void tryPersistTest() {
        Task t = dp.getAllTasks().get(0);

        assertTrue("Task persist not work", dp.tryPersist(t));
    }
    @Test
    public void getUncachedEntityTest() {
        Employee e = dp.getEmployeeEntityByName("Evgenij Tsopa");

        assertTrue("Could not get uncached entity by name", e != null);
        assertTrue("Got wrong entity (uncached test)", e.getNickName().equals("jek"));
    }
    @Test
    public void getCachedEntityTest() {
        Status s = dp.getAllStatuses().get(0);

        assertTrue("could not get cached entity", s != null);
    }
    @Test
    public void getEntityByIdTest() {
        Task t = dp.getTaskEntityById(3485);

        assertTrue("could not get entity by id", t != null);
    }
    @Test
    public void getUncachedEntityList() {
        List<Task> tasks = dp.getAllTasks();

        assertTrue("could not get uncached entity list", tasks != null);
        assertTrue("While getting uncached entity list expected >9000 entities, but got " + tasks.size(), tasks.size() > 9000);
    }
    @Test
    public void getCachedEntityList() {
        List<Priority> priorities = dp.getAllPriorities();

        assertTrue("Could not get cached entity list (or list is too small)", priorities != null && priorities.size() > 5 );
    }
    @Test
    public void getTasksWithFiltersTest() {
        Map<String, String> filters = new HashMap<>();

        filters.put("Status", "открыта");
        filters.put("Priority", "низкий");

        List<Task> tasks = dp.getTasks(filters);

        assertTrue("Could not get Tasks with filters", tasks != null);
        assertTrue("Tasks filters do not work", tasks.size() < 9000 && tasks.size() > 0);
    }
}