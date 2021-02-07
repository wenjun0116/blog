package com.qiufeng.blog.service;

import com.qiufeng.blog.entity.SysUser;

public interface UserService {

    SysUser checkUser(String userName, String password);

    SysUser findById(Long id);

    SysUser findByEmailAndNickName(String email, String nickName);

}
