package com.qiufeng.blog.dao;

import com.qiufeng.blog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

    User findByUserNameAndPassword(String userName,String password);
}
