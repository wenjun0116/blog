package com.qiufeng.blog.service.impl;

import com.qiufeng.blog.dao.TagRepository;
import com.qiufeng.blog.entity.Tag;
import com.qiufeng.blog.exception.NotFoundException;
import com.qiufeng.blog.service.TagService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    @Resource
    TagRepository tagRepository;

    @Override
    public Tag saveTag(Tag tag) {
        return tagRepository.save(tag);
    }

    @Override
    public Tag getTag(Long id) {
        return tagRepository.getOne(id);
    }
    @Override
    public Page<Tag> listTag(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }

    @Override
    public Tag updateTag(Long id, Tag tag) {
        Tag one = tagRepository.getOne(id);
        if (one == null) {
            throw new NotFoundException("不存在该类型");
        }
        BeanUtils.copyProperties(tag,one);
        return tagRepository.save(one);
    }

    @Override
    public void deleteTag(Long id) {
        tagRepository.deleteById(id);
    }
    @Override
    public Tag getTagByName(String name) {
        return tagRepository.findByName(name);
    }
    @Override
    public List<Tag> listTag() {
        return tagRepository.findAll();
    }
    @Override
    public List<Tag> listTag(String ids) {
        return tagRepository.findAllById(converToList(ids));
    }
    @Override
    public List<Tag> listTagTop(Integer size) {
        return tagRepository.findTop(PageRequest.of(0,size, Sort.by(Sort.Direction.DESC,"blogs.size")));
    }

    private List<Long> converToList(String ids){
        List<Long> list = new ArrayList<>();
        if (!"".equals(ids) && ids != null){
            String[] idarray = ids.split(",");
            for (int i = 0; i < idarray.length ; i++) {
                list.add(new Long(idarray[i]));
            }
        }
        return list;
    }
}
