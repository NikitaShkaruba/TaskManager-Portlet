package elcom.ejbs;

import elcom.Entities.*;

import javax.ejb.Local;
import java.util.List;

public interface DatabaseConnector {
    // CREATE Methods
    boolean tryCreateTask(Task task);
    boolean tryCreateComment(Comment comment);

    // READ Methods
    Comment readCommentByContent(String content);
    Employee readEmployeeByName(String name);
    Group readGroupByName(String name);
    Priority readPriorityByName(String name);
    Status readStatusByName(String name);
    Task readTaskById(long id);

    List<Comment> readAllComments();
    List<Comment> readTaskComments(Task task);
    List<Task> readTasks(String statusFilter, String employeeFilter);

    List<String> readEmployeesAsStrings();
    List<String> readGroupsAsStrings();
    List<String> readPrioritiesAsStrings();
    List<String> readStatusesAsStrings();

    // UPDATE Methods
    boolean tryUpdateTask(Task task);
    boolean tryUpdateComment(Comment comment);

    // DELETE Methods
    boolean tryDeleteTask(Task task);
    boolean tryDeleteComment(Comment comment);
}
