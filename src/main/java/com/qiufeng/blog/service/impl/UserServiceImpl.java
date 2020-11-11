package com.qiufeng.blog.service.impl;

import com.qiufeng.blog.dao.UserRepository;
import com.qiufeng.blog.entity.User;
import com.qiufeng.blog.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService{

    @Resource
    UserRepository userRepository;

    @Override
    @Transactional
    public User checkUser(String userName, String password) {
        password = DigestUtils.md5Hex(password);
        User user = userRepository.findByUserNameAndPassword(userName,password);
        return user;
    }

    @Override
    public User findById(Long id) {
        return userRepository.getOne(id);
    }
}
