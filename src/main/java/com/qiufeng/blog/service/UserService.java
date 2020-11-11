package com.qiufeng.blog.service;

import com.qiufeng.blog.entity.User;

public interface UserService {

    User checkUser(String userName, String password);

    User findById(Long id);
}
