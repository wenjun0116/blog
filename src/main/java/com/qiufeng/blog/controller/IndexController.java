package com.qiufeng.blog.controller;

import com.qiufeng.blog.entity.Blog;
import com.qiufeng.blog.entity.Comment;
import com.qiufeng.blog.service.BlogService;
import com.qiufeng.blog.service.CommentService;
import com.qiufeng.blog.service.TagService;
import com.qiufeng.blog.service.TypeService;
import com.qiufeng.blog.util.CommentUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class IndexController {

    @Autowired
    BlogService blogService;

    @Autowired
    TypeService typeService;

    @Autowired
    TagService tagService;

    @Autowired
    CommentService commentService;

    @GetMapping("/")
    public String getBlogs(@PageableDefault(size = 8,sort = {"updateTime"},
            direction = Sort.Direction.DESC) Pageable pageable, Model model){
        Page<Blog> blogs = blogService.listPublishedBlog(pageable);
        model.addAttribute("page",blogs);
        model.addAttribute("types",typeService.listTypeTop(6));
        model.addAttribute("tags",tagService.listTagTop(10));
        model.addAttribute("recommendBlogs",blogService.listRecommendBlogTop(8));
        return "index";
    }

    @PostMapping("/search")
    public String search(@PageableDefault(size = 8,sort = {"updateTime"},
            direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam("query")String query, Model model){
        model.addAttribute("page",blogService.listBlog("%"+query+"%",pageable));
        model.addAttribute("query",query);
        return "search";
    }

    @GetMapping("/blog/{id}")
    public String Blog(@PathVariable("id") Long id,Model model){
        Blog blog = blogService.getAndConvert(id);
        model.addAttribute("blog",blog);
        List<Comment> comments = commentService.listCommentByBlogId(id);
        CommentUtil util = new CommentUtil();
        List<Comment> childComment = util.getChildComment(comments,this.commentService);
        model.addAttribute("comments",childComment);
        return "blog";
    }

    @GetMapping("/footer/newBlogs")
    public String newBlogs(Model model){
        model.addAttribute("newBlogs",blogService.listRecommendBlogTop(3));
        String s = "_fragments :: newBlogs";
        return s;
    }
}
