package elcom.tabs;

import elcom.entities.Comment;

// Todo: merge with MoreTab
public interface Commentable {
    Comment getNewCommentary();
    void setNewCommentary(Comment commentary);
}
