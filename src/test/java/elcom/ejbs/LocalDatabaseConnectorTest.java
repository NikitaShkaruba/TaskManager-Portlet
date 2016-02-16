package elcom.ejbs;

import static org.junit.Assert.assertTrue;

import elcom.entities.*;
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
        List<Status> statuses = dbc.readAllStatuses();

        assertTrue("Status Test failed at reading statuses", !statuses.isEmpty());

        assertTrue("Status color is not working", statuses.get(0).getColor() != null);
    }
    @Test
    public void GroupTest() {
        List<Group> groups = dbc.readAllGroups();

        assertTrue(!groups.isEmpty());
    }
    @Test
    public void TaskTemplateTest() {
        List<TaskTemplate> tts = dbc.readAllTaskTemplates();

        assertTrue(!tts.isEmpty());
    }
    @Test
    public void VendorsTest() {
        List<Vendor> vendors = dbc.readAllVendors();

        assertTrue(!vendors.isEmpty());
    }
    @Test
    public void OrganisationsTest() {
        List<Contact> organisations = dbc.readAllOrganisations();

        assertTrue(!organisations.isEmpty());
    }
}