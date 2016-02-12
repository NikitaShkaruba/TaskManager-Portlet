package elcom.ejbs;

import static org.junit.Assert.assertTrue;

import elcom.Entities.Comment;
import elcom.Entities.Status;
import elcom.Entities.Task;
import elcom.Entities.TaskTemplate;
import org.junit.Before;
import org.junit.Test;
import java.util.List;

public class LocalDatabaseConnectorTest {
    LocalDatabaseConnector dbc;

    @Before
    public void SetupDatabaseConnector() {
        dbc = new LocalDatabaseConnector();
    }
    @Test
    public void GetAtLeastOneTaskTest() {
        List<Task> tasks = dbc.readTasks("Любой", "Все");
        assertTrue(!tasks.isEmpty());
    }
    @Test
    public void GetAllDescriptionsTest() {
        List<Comment> comments = dbc.readAllComments();
        assertTrue(!comments.isEmpty());
    }
    @Test
    public void GetDescriptionsForTaskTest() {
        Task task = dbc.readTasks("Любой", "Все").get(0);
        List<Comment> comments = dbc.readTaskComments(task);
        assertTrue(!comments.isEmpty());
    }
    @Test
    public void StatusTest() {
        List<String> statuses = dbc.readStatusesAsStrings();

        assertTrue("Status Test failed at reading statuses", !statuses.isEmpty());

        Status status = dbc.readStatusByName(statuses.get(0));

        assertTrue("Status color is not working", status.getColor() != null);
    }
    @Test
    public void GroupTest() {
        List<String> groups = dbc.readGroupsAsStrings();

        assertTrue(!groups.isEmpty());
    }
    @Test
    public void TaskTemplateTest() {
        List<TaskTemplate> tts = dbc.readAllTaskTemplates();

        assertTrue(!tts.isEmpty());
    }
    @Test
    public void VendorsTest() {
        List<String> vendors = dbc.readVendorsAsStrings();

        assertTrue(!vendors.isEmpty());
    }
    @Test
    public void OrganisationsTest() {
        List<String> organisations = dbc.readOrganisationsAsStrings();

        assertTrue(!organisations.isEmpty());
    }
}