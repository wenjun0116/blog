package com.qiufeng.blog.service.impl;

import com.qiufeng.blog.dao.UserRepository;
import com.qiufeng.blog.entity.SysUser;
import com.qiufeng.blog.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService{

    @Resource
    UserRepository userRepository;

    @Override
    @Transactional
    public SysUser checkUser(String userName, String password) {
        password = DigestUtils.md5Hex(password);
        SysUser sysUser = userRepository.findByUserNameAndPassword(userName,password);
        return sysUser;
    }

    @Override
    public SysUser findById(Long id) {
        return userRepository.getOne(id);
    }

    @Override
    public SysUser findByEmailAndNickName(String email, String nickName) {
        return  userRepository.findByEmailAndNickName(email, nickName);
    }
}
