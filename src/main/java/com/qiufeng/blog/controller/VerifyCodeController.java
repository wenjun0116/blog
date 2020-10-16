package com.qiufeng.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import java.util.UUID;

@Controller
public class VerifyCodeController {
    private static final long serialVersionUID = 1L;

    @RequestMapping("/getVerifyCode")
    public void getVerifyCode(HttpServletResponse resp, HttpSession session) throws IOException {
        //生成随机数
        String randomCode = UUID.randomUUID().toString().substring(0,5);

        //把随机数放入Session中
        session.setAttribute("RANDOMCODE_IN_SESSION",randomCode);

        //创建图片对象
        int width = 100;
        int height = 40;
        int imageType = BufferedImage.TYPE_INT_RGB;
        BufferedImage img = new BufferedImage(width,height,imageType);

        //画板
        Graphics g = img.getGraphics();
        g.setColor(Color.white);

        //绘制一个实心矩形
        g.fillRect(1,1,width-2,height-2);

        //把随机数画入图片中
        //设置随机数颜色
        g.setColor(Color.BLACK);
        Font font = new Font("宋体",Font.BOLD+Font.ITALIC,20);
        g.setFont(font);
        //设置随机数的字体和大小
        g.drawString(randomCode,10,28);

        //干扰线
        g.setColor(Color.GRAY);
        Random r = new Random();
        for (int i =0;i<100;i++){
            g.fillRect(r.nextInt(width),r.nextInt(height),2,2);
        }

        //关闭
        g.dispose();
        //把图片以流的方式写给请求对象
        ImageIO.write(img,"jpg",resp.getOutputStream());
    }

}
