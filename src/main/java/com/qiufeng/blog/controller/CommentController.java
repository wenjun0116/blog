package com.qiufeng.blog.controller;

import com.qiufeng.blog.entity.Comment;
import com.qiufeng.blog.entity.User;
import com.qiufeng.blog.service.BlogService;
import com.qiufeng.blog.service.CommentService;
import com.qiufeng.blog.util.CommentUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.List;

@Controller
public class CommentController {

    @Autowired
    CommentService commentService;

    @Autowired
    BlogService blogService;

    @Resource
    JavaMailSenderImpl javaMailSender;

    /**
     * 取出配置文件中评论用户默认头像
     */
    @Value("${comment.avatar}")
    String avatar;

    //根据博客Id获取该博客下的评论信息
    @GetMapping("/comments/{blogId}")
    public String comments(@PathVariable("blogId")Long blogId, Model model){
        List<Comment> comments = commentService.listCommentByBlogId(blogId);
        CommentUtil util = new CommentUtil();
        model.addAttribute("comments",util.getChildComment(comments,this.commentService));
        return "blog :: commentList";
    }

    //提交评论
    @PostMapping("/comments")
    public String postComments(Comment comment, HttpSession session){
        Long blogId = comment.getBlog().getId();
        comment.setBlog(blogService.getAndConvert(blogId));
        comment.setAvatar(avatar);
        if (session.getAttribute("user")!=null){
            comment.setAdmin(true);
            comment.setAvatar(((User)session.getAttribute("user")).getAvatar());
        }
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        try {
            commentService.saveComment(comment);
            //提示博主有人评论了这篇博客
            if (!comment.isAdmin()){
                //博主发送的评论不用提示
                helper.setText("<h3>网友：<span style='color: red !important;'>"+comment.getNickName()+"</span>评论了您的博客</h3>" +
                        "<p><a href='https://www.qfblog.top:8888/blog/"+blogId+"'>快去看看吧</a></p>",true);
                helper.setTo(comment.getEmail());
                helper.setFrom("2404240896@qq.com");
                javaMailSender.send(mimeMessage);
            }
            //判断是否有父级评论
            if (comment.getParentComment() != null) {
                if (comment.getParentComment().getId() != null) {
                    Comment parentComment = commentService.findById(comment.getParentComment().getId());
                    helper.setSubject("您在QfBlogs的评论，收到一条回复。");
                    //判断是否是博主回复了该评论
                    if (comment.isAdmin()){
                        helper.setText("<h3>博主：<span style='color: red !important;'>"+comment.getNickName()+"</span> 回复了您的评论</h3>" +
                                "<p><a href='https://www.qfblog.top:8888/blog/"+blogId+"'>快去看看吧</a></p>",true);
                    }else{
                        helper.setText("<h3>网友：<span style='color: red !important;'>"+comment.getNickName()+"</span> 回复了您的评论</h3>" +
                                "<p><a href='https://www.qfblog.top:8888/blog/"+blogId+"'>快去看看吧</a></p>",true);
                    }
                    helper.setTo(parentComment.getEmail());
                    helper.setFrom("2404240896@qq.com");
                    javaMailSender.send(mimeMessage);
                }
            }
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return "redirect:/comments/"+blogId;
    }
}
