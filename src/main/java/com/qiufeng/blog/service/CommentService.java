package com.qiufeng.blog.service;

import com.qiufeng.blog.entity.Comment;

import java.util.List;

public interface CommentService {

    List<Comment> listCommentByBlogId(Long blogId);

    Comment saveComment(Comment comment);

    List<Comment> listCommentByParentCommentId(Long parentCommentId);

    Comment findById(Long id);
}
