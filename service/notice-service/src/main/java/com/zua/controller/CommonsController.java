package com.zua.controller;

import com.zua.util.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * 文件的上传与下载
 */
@Slf4j
@RestController
@RequestMapping("/common")
@CrossOrigin
public class CommonsController {

    @Value("${zua.path}")
    private String basePath;

    /**
     * 文件上传
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public R uploadFile(MultipartFile file) {
        //获取原始文件名称
        String originalFilename = file.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));

        //使用UUID重新生成文件名,防止文件覆盖
        String fileName = UUID.randomUUID().toString() + suffix;
        //判断是否存在目录
        File dir = new File(basePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        //将临时文件转储到指定位置
        try {
            file.transferTo(new File(basePath + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return R.SUCCESS("",fileName);
    }

    /**
     * 图片下载   回显
     * @param name
     * @param response
     * @throws IOException
     */
    @GetMapping("/download")
    public void download(String name, HttpServletResponse response) throws IOException {

        FileInputStream fileInputStream = null;

        ServletOutputStream outputStream = null;



        try{
            //输入流,通过输入流读取文件内容
            fileInputStream = new FileInputStream(new File(basePath + name));
            //输出流,通过输出流将文件写回浏览器,在浏览器展示图片
            outputStream = response.getOutputStream();

            response.setContentType("image/jpeg");

            byte[] bytes = new byte[1024];
            int len = 0;
            while ((len = fileInputStream.read(bytes)) != -1) {
                outputStream.write(bytes,0,len);
                outputStream.flush();
            }

        }catch (IOException ex) {
            ex.printStackTrace();
        }finally {
            //释放资源
            if (fileInputStream != null) {
                fileInputStream.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
        }
    }
}