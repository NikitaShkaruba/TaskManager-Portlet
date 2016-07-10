package elcom.tabs;

import elcom.entities.Comment;
import elcom.entities.Task;
import java.util.ArrayList;


public class MoreTab extends Tab implements TaskSelector, Commentable {
    private Task selectedTask;
    private Comment newComment;

    public MoreTab(Task task) {
        this.tasks = new ArrayList();
        this.newComment = new Comment();
        this.newComment.setContent("");

        tasks.add(task);
    }

    @Override
    public String getTitle() {
        return "Заявка №" + tasks.get(0).getId();
    }

    // Proxy logic for TaskPresenter
    @Override
    public Task getSelectedTask() {
        return selectedTask;
    }
    @Override
    public void setSelectedTask(Task selectedTask) {
        this.selectedTask = selectedTask;
    }
    @Override
    public Comment getNewCommentary() {
        return newComment;
    }
    @Override
    public void setNewCommentary(Comment commentary) {
        this.newComment = commentary;
    }
}
