package com.qiufeng.blog.dao;

import com.qiufeng.blog.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image,Long> {

    Image findByUrl(String url);

}
