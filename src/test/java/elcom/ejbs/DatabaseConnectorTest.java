package elcom.ejbs;

import static org.junit.Assert.assertTrue;

import elcom.Entities.Task;
import org.junit.Test;
import java.util.List;

public class DatabaseConnectorTest {
    @Test
    public void GetAtLeastOneTaskTest() {
        List<Task> tasks = new DatabaseConnector().retrieveTasks("Любой", "Все");
        assertTrue("Zero tasks retrieved", !tasks.isEmpty());
    }
}