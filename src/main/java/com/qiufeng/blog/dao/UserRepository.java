package com.qiufeng.blog.dao;

import com.qiufeng.blog.entity.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<SysUser,Long> {

    SysUser findByUserNameAndPassword(String userName, String password);

    SysUser findByEmailAndNickName(String email, String nickName);
}
