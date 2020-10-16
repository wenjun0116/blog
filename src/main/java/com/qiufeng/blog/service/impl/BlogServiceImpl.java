package com.qiufeng.blog.service.impl;

import com.qiufeng.blog.dao.BlogRepository;
import com.qiufeng.blog.entity.Blog;
import com.qiufeng.blog.entity.Type;
import com.qiufeng.blog.exception.NotFoundException;
import com.qiufeng.blog.service.BlogService;
import com.qiufeng.blog.util.MarkdownUtils;
import com.qiufeng.blog.util.MyBeanUtils;
import com.qiufeng.blog.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.criteria.*;
import java.util.*;

@Service
public class BlogServiceImpl implements BlogService{

    @Resource
    BlogRepository blogRepository;

    @Override
    public Blog getBlog(Long id) {
        return blogRepository.getOne(id);
    }

    @Transactional
    @Override
    public Blog getAndConvert(Long id) {
        Blog blog = blogRepository.getOne(id);
        if (blog == null) {
            throw new NotFoundException("找不到该数据！");
        }
        Blog b = new Blog();
        BeanUtils.copyProperties(blog,b);
        String s = MarkdownUtils.markdownToHtmlExtensions(blog.getContent());
        b.setContent(s);
        blogRepository.updateViews(id);
        return b;
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable, BlogQuery blog) {
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (!"".equals(blog.getTitle()) && blog.getTitle() != null){
                    predicates.add(criteriaBuilder.like(root.<String>get("title"),"%"+blog.getTitle()+"%"));

                }
                if (blog.getTypeId() != null){
                    predicates.add(criteriaBuilder.equal(root.<Type>get("type").get("id"),blog.getTypeId()));
                }
                if (blog.isRecommend()){
                    predicates.add(criteriaBuilder.equal(root.<Boolean>get("recommend"),blog.isRecommend()));
                }
                criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        },pageable);
    }
    @Override
    public Page<Blog> listBlog(Pageable pageable) {
        return blogRepository.findAll(pageable);
    }
    @Override
    public Page<Blog> listBlog(Long tagId, Pageable pageable){
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Join join = root.join("tags");
                return criteriaBuilder.equal(join.get("id"),tagId);
            }
        },pageable);
    }
    @Override
    public Map<String, List<Blog>> archiveBlog() {
        List<String> groupYear = blogRepository.findGroupYear();
        Map<String,List<Blog>> map = new HashMap<>();
        for (String year : groupYear){
            map.put(year,blogRepository.findByYear(year));
        }
        return map;
    }
    @Override
    public Long countBlog() {
        return blogRepository.count();
    }

    @Override
    public Page<Blog> listPublishedBlog(Pageable pageable) {
        return blogRepository.listBlog(pageable);
    }

    @Transactional
    @Override
    public Blog saveBlog(Blog blog) {
            if (blog.getId() == null){
                blog.setCreateTime(new Date());
                blog.setUpdateTime(new Date());
                blog.setViews(0);
            }else {
                blog.setUpdateTime(new Date());
            }
            return blogRepository.save(blog);
    }
    @Transactional
    @Override
    public Blog updateBlog(Long id, Blog blog) {
        Blog one = blogRepository.getOne(id);
        if (one == null) {
            throw new NotFoundException("修改的博客不存在");
        }
        BeanUtils.copyProperties(blog,one, MyBeanUtils.getNullPropertyName(blog));
        one.setUpdateTime(new Date());
        return blogRepository.save(one);
    }

    @Transactional
    @Override
    public void deleteBlog(Long id) {
        blogRepository.deleteById(id);
    }
    @Override
    public List<Blog> listRecommendBlogTop(Integer size) {
        return blogRepository.findTop(PageRequest.of(0,size, Sort.by(Sort.Direction.DESC,"updateTime")));
    }
    @Override
    public Page<Blog> listBlog(String query, Pageable pageable) {
        return blogRepository.findByQuery(query,pageable);
    }

}
