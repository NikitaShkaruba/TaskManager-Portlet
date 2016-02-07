package elcom.main;

import static org.junit.Assert.assertTrue;

import elcom.Entities.Task;
import org.junit.Test;
import java.util.List;

public class DatabaseConnectorTest {
    @Test
    public void GetAtLeastOneTaskTest() {
        List<Task> tasks = DatabaseConnector.getTasks("Любой", "Все");
        assertTrue("Zero tasks retrieved", !tasks.isEmpty());
    }
}