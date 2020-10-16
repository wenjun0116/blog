package com.qiufeng.blog;

import com.qiufeng.blog.entity.Blog;
import com.qiufeng.blog.entity.Comment;
import com.qiufeng.blog.entity.Tag;
import com.qiufeng.blog.service.BlogService;
import com.qiufeng.blog.service.CommentService;
import com.qiufeng.blog.util.CommentUtil;
import com.qiufeng.blog.util.MarkdownUtils;
import org.junit.internal.Classes;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.DigestUtils;
import sun.security.provider.MD5;

import javax.annotation.Resource;
import java.beans.Transient;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
class BlogApplicationTests {

	@Resource
	BlogService blogService;
//
////	@Autowired
////	CommentService commentService;
////
////	@Resource
////	JavaMailSenderImpl javaMailSender;
//
////	@Autowired
////	RedisTemplate redisTemplate;
//
//	@Test
//	void contextLoads() {
////		Blog blog = blogService.getBlog((long) 34);
////		List<Comment> comments = commentService.listCommentByBlogId(blog.getId());
////		System.out.println(getChildComment(comments));
//	}
//
//	@Test
//	void testEmail(){
////		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
////		simpleMailMessage.setSubject("通知今晚开会");
////		simpleMailMessage.setText("今晚 7:00");
////		simpleMailMessage.setTo("2270600597@qq.com");
////		simpleMailMessage.setFrom("2404240896@qq.com");
////		javaMailSender.send(simpleMailMessage);
//	}

    @Test
    void testMarkdown(){
//       String content =  blogService.getBlog((long) 60).getContent();
        String content ="```c\n" +
                "#include <stdio.h\n>\n" +
                "int main()\n" +
                "{\n" +
                "return 0;\n" +
                "}```";
        String mymarkdown = MarkdownUtils.myMarkdownToHtml(content);
        String markdown = MarkdownUtils.markdownToHtmlExtensions(content);
        System.out.println("==========markdown"+markdown);
        System.out.println("==========mymarkdown"+mymarkdown);
//        System.out.println(content);
    }
}
