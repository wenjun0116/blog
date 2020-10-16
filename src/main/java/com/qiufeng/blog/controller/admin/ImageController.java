package com.qiufeng.blog.controller.admin;

import com.qiufeng.blog.configuration.PreReadUploadConfig;
import com.qiufeng.blog.entity.Image;
import com.qiufeng.blog.service.ImageService;
import com.qiufeng.blog.util.FileTool;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;

@RequestMapping("/admin")
@Controller
public class ImageController {

    @Resource
    ImageService imageService;

    @Resource
    PreReadUploadConfig uploadConfig;

    @GetMapping("/images")
    public String showImages(@PageableDefault(size = 10,sort = {"id"},direction = Sort.Direction.DESC)
                                         Pageable pageable, Model model){
        model.addAttribute("page",imageService.listImage(pageable));
        return "admin/images";
    }

    @PostMapping("/images/insertImage")
    public String insertImage(Image image, BindingResult result
            , MultipartFile file, RedirectAttributes attributes){
        if (file!=null){
            //判断文件大小和文件格式是否正确
            int fileSize = 5000000;
            if (fileSize < file.getSize()){
                result.rejectValue("name","nameError","上传文件过大");
                return "admin/images-input";
            }
            //获取文件名
            String fileName = file.getOriginalFilename();
            //判断是否为图片格式
            if (fileName.indexOf(".jpg")!=-1 || fileName.indexOf(".png")!=-1
                    || fileName.indexOf(".jpeg")!=-1 || fileName.indexOf(".gif")!=-1){
                //重新设置图片名
                String newFileName = image.getName()+".jpg";
                //判断是否有该名称
                Image img = imageService.findByUrl("https://www.qfblog.top:8888/static/"+newFileName);
                if (img==null){
                    try{
                        FileTool.uploadFiles(file.getBytes(),uploadConfig.getUploadPath(),newFileName);
                        System.out.println("------------------上传文件成功-----------------");
                        image.setUrl("/static/"+newFileName);
                        image.setCreateTime(new Date());
                        if (image.getId()==null){
                            imageService.saveImage(image);
                        }else {
                            imageService.updateImage(image);
                        }
                        attributes.addFlashAttribute("message","上传成功");
                    }catch (Exception e){
                        result.rejectValue("name","nameError","系统错误，请重试");
                        System.out.println(e.getLocalizedMessage());
                        return "admin/images-input";
                    }
                }else {
                    result.rejectValue("name","nameError","该文件名已存在，请重试");
                    return "admin/images-input";
                }
            }else{
                result.rejectValue("name","nameError","上传图片格式不正确");
                return "admin/images-input";
            }
        }
        return "redirect:/admin/images";
    }

    @GetMapping("/images/input")
    public String showInputImage(Model model){
        model.addAttribute("image",new Image());
        return "admin/images-input";
    }

    @GetMapping("/images/{id}/input")
    public String getImage(@PathVariable("id")Long id, Model model){
        model.addAttribute("image",imageService.getOne(id));
        return "admin/images-input";
    }

    @GetMapping("/images/{id}/delete")
    public String delImage(@PathVariable("id")Long id,RedirectAttributes attributes){
        imageService.delImage(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/images";
    }

    /**
     * 获取当前系统路径
     */
    private String getUploadPath() {
        File path = null;
        try {
            path = new File(ResourceUtils.getURL("classpath:").getPath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (!path.exists()) path = new File("");
        File upload = new File(path.getAbsolutePath(), "static/upload/");
        if (!upload.exists()) upload.mkdirs();
        return upload.getAbsolutePath();
    }
}
