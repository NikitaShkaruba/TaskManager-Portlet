package elcom.tabs;

import elcom.entities.Comment;
import elcom.entities.Task;
import elcom.entities.TaskFile;
import org.primefaces.model.UploadedFile;
import java.util.ArrayList;
import java.util.List;

public class MoreTab extends Tab implements TaskSelector, Commentable {
    private Task selectedTask;
    private Comment newComment;
    private List<TaskFile> attachedFiles;
    private UploadedFile newAttachment;

    public MoreTab(Task task) {
        this.tasks = new ArrayList();
        this.newComment = new Comment();

        tasks.add(task);
        selectedTask = task;
    }

    @Override
    public String getTitle() {
        return "Заявка №" + tasks.get(0).getId();
    }

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

    public List<TaskFile> getAttachedFiles() {
        return attachedFiles;
    }
    public void setAttachedFiles(List<TaskFile> attachments) {
        this.attachedFiles = attachments;
    }

    public UploadedFile getNewAttachment() {
        return newAttachment;
    }
    public void setNewAttachment(UploadedFile newAttachment) {
        this.newAttachment = newAttachment;
    }
}
