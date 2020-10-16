package com.qiufeng.blog.service.impl;

import com.qiufeng.blog.dao.TypeRepository;
import com.qiufeng.blog.entity.Type;
import com.qiufeng.blog.exception.NotFoundException;
import com.qiufeng.blog.service.TypeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class TypeServiceImpl implements TypeService {

    @Resource
    private TypeRepository typeRepository;

    @Override
    @Transactional
    public Type saveType(Type type) {
        return typeRepository.save(type);
    }
    @Override
    @Transactional
    public Type getType(Long id) {
        return typeRepository.getOne(id);
    }
    @Override
    @Transactional
    public Page<Type> listType(Pageable pageable) {
        return typeRepository.findAll(pageable);
    }
    @Override
    public List<Type> listTypeTop(Integer size) {
        return typeRepository.findTop(PageRequest.of(0,size,Sort.by(Sort.Direction.DESC,"blogs.size")));
    }

    @Override
    @Transactional
    public Type updateType(Long id, Type type) {
        Type t = typeRepository.getOne(id);
        if (t == null){
            throw new NotFoundException("不存在该类型");
        }
        BeanUtils.copyProperties(type,t);
        return typeRepository.save(t);
    }

    @Override
    @Transactional
    public void deleteType(Long id) {
        typeRepository.deleteById(id);
    }
    @Override
    public Type getTypeByName(String name) {
        return typeRepository.findByName(name);
    }
    @Override
    public List<Type> listType() {
        return typeRepository.findAll();
    }
}
