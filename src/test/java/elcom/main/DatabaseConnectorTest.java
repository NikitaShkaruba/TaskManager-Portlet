package elcom.main;

import static org.junit.Assert.assertTrue;
import org.junit.Test;
import java.util.List;

public class DatabaseConnectorTest {
    DatabaseConnector databaseConnector = new DatabaseConnector();

    @Test
    public void GetAtLeastOneTaskTest() {
        List<Task> tasks = databaseConnector.getTasks("Любой", "Все");
        assertTrue("Zero tasks retrieved", tasks.size() != 0);
    }
}