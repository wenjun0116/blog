package com.qiufeng.blog.service.impl;

import com.qiufeng.blog.dao.CommentRepository;
import com.qiufeng.blog.entity.Comment;
import com.qiufeng.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService{

    @Resource
    CommentRepository commentRepository;
    @Override
    public List<Comment> listCommentByBlogId(Long blogId) {
        List<Comment> comments = commentRepository.findByBlogId(blogId, Sort.by( "createTime"));
        return comments;
    }

    @Transactional
    @Override
    public Comment saveComment(Comment comment) {
        Long parentComment = comment.getParentComment().getId();
        if (parentComment != -1){
            comment.setParentComment(commentRepository.getOne(parentComment));
        }else{
            comment.setParentComment(null);
        }
        comment.setCreateTime(new Date());
        return commentRepository.save(comment);
    }
    @Override
    public List<Comment> listCommentByParentCommentId(Long parentCommentId) {
        return commentRepository.findByParentCommentId(parentCommentId,Sort.by("createTime"));
    }
    @Override
    public Comment findById(Long id) {
        return commentRepository.getOne(id);
    }

}
