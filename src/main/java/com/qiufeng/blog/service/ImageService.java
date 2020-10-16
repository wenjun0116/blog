package com.qiufeng.blog.service;

import com.qiufeng.blog.entity.Image;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ImageService {

    Image getOne(Long id);

    void delImage(Long id);

    Image saveImage(Image image);

    Image findByUrl(String url);

    List<Image> getAll();

    Image updateImage(Image image);

    Page<Image> listImage(Pageable pageable);

}
