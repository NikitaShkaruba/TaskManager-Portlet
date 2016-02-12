package elcom.ejbs;

import static org.junit.Assert.assertTrue;

import elcom.Entities.Description;
import elcom.Entities.Status;
import elcom.Entities.Task;
import org.junit.Before;
import org.junit.Test;
import java.util.List;

public class DatabaseConnectorTest {
    DatabaseConnector dbc;

    @Before
    public void SetupDatabaseConnector() {
        dbc = new DatabaseConnector();
    }

    @Test
    public void GetAtLeastOneTaskTest() {
        List<Task> tasks = dbc.readTasks("Любой", "Все");
        assertTrue(!tasks.isEmpty());
    }

    @Test
    public void GetAllDescriptionsTest() {
        List<Description> descriptions = dbc.readDescriptions(null);
        assertTrue(!descriptions.isEmpty());
    }

    @Test
    public void GetDescriptionsForTaskTest() {
        Task task = dbc.readTasks("Любой", "Все").get(0);
        List<Description> descriptions = dbc.readDescriptions(task);
        assertTrue(!descriptions.isEmpty());
    }

    @Test
    public void StatusTest() {
        List<String> statuses = dbc.readStatuses();

        assertTrue("Status Test failed at reading statuses", !statuses.isEmpty());

        Status status = dbc.findStatusByName(statuses.get(0));

        assertTrue("Status color is not working", status.getColor() != null);
    }

    @Test
    public void GroupTest() {
        List<String> groups = dbc.readGroups();

        assertTrue(!groups.isEmpty());
    }

}