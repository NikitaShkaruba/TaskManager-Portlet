package elcom;

/**
 * Created by Nikita Shkaruba on 12.01.16.
 * <p>
 * My Contacts:
 * Email: sh.nickita@list.ru
 * GitHub: github.com/SigmaOne
 * Vk: vk.com/wavemeaside
 */

// TODO: 12.01.16 Make this bean an entity and map it to database
public class Task {
    private int id;
    private String description;
    private String executor;
    private TaskStatus status;

    public Task(int id, String description, String executor, TaskStatus status) {
        this.id = id;
        this.description = description;
        this.executor = executor;
        this.status = status;
    }
    public Task() {

    }

    public String getId() {
        return id + "";
    }
    public String getDescription() {
        return description;
    }
    public String getExecutor() {
        return executor;
    }
    public String getStatus() {
        return status.name().toLowerCase();
    }
}
