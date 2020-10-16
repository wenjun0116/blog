package com.qiufeng.blog.util;

import com.qiufeng.blog.entity.Comment;
import com.qiufeng.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

public class CommentUtil {

    List<Comment> comments;

    List<Comment> removeComment = new ArrayList<>();

    public List<Comment> getChildComment(List<Comment> comments,CommentService commentService){
        for(Comment comment :comments){
            if (comment.getParentComment() == null){
                this.comments = new ArrayList<>();
                List<Comment> childComment = commentService.listCommentByParentCommentId(comment.getId());
                comment.setReplyComments(getChildsComment(childComment,commentService));
            }else{
                removeComment.add(comment);
            }
        }
        comments.removeAll(removeComment);
        return comments;
    }

    public List<Comment> getChildsComment(List<Comment> comments,CommentService commentService) {
        if (comments != null) {
            for (Comment comment : comments) {
                this.comments.add(comment);
                comment.setReplyComments(commentService.listCommentByParentCommentId(comment.getId()));
                getChildsComment(comment.getReplyComments(), commentService);
            }
        }
        return this.comments;
    }

}
