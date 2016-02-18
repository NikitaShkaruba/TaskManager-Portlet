package elcom.tabs;

import elcom.entities.Comment;

public interface Commentable {
    Comment getNewCommentary();
    void setNewCommentary(Comment commentary);
}
