package com.qiufeng.blog.service.impl;

import com.qiufeng.blog.dao.ImageRepository;
import com.qiufeng.blog.entity.Image;
import com.qiufeng.blog.service.ImageService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class ImageServiceImpl implements ImageService {

    @Resource
    ImageRepository imageRepository;

    @Override
    public Image getOne(Long id) {
        return imageRepository.getOne(id);
    }

    @Override
    public void delImage(Long id) {
        imageRepository.deleteById(id);
    }

    @Override
    public Image saveImage(Image image) {
        return imageRepository.save(image);
    }

    @Override
    public Image findByUrl(String url) {
        return imageRepository.findByUrl(url);
    }

    @Override
    public List<Image> getAll() {
        return imageRepository.findAll();
    }

    @Override
    public Image updateImage(Image image) {
        if (image.getId()!=null){
            Image one = imageRepository.getOne(image.getId());
            if (one!=null){
                BeanUtils.copyProperties(image,one);
            }
            return imageRepository.save(one);
        }
        return null;
    }

    @Override
    public Page<Image> listImage(Pageable pageable) {
        return imageRepository.findAll(pageable);
    }
}
