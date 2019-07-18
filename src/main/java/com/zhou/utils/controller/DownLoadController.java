package com.zhou.utils.controller;

import com.alibaba.fastjson.JSONObject;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

/**
 * @author : Wenyu Zhou
 * @version : v1.0
 * @date : 2019/6/18
 * description : 描述
 */
@RestController
@RequestMapping("/download")
public class DownLoadController {
    @Value("${server.port}")
    private String port;

    @PostMapping(value = "/test")
    public String test(){
        System.out.println("来调用了");
        JSONObject js = new JSONObject();
        js.put("msg","来自" + port + "端口的hello");
        return js.toJSONString();
    }
    /**
     * 下载文件工具
     * http://192.168.10.148:9998/download/file/qsjd.jar
     * */
    @GetMapping(value = "/file/{filename}")
    public void doDownLoadFile(HttpServletResponse response,
                                  HttpServletRequest request,@PathVariable String filename) throws Exception {
        // 清空response 所有信息
        response.reset();
        // 设置IE浏览器内容类型 表示为 下载
        response.setContentType("application/x-download;charset=UTF-8");
        // 设置IE浏览器的 头
        if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > 0) {
            // firefox浏览器
            filename = new String(filename.getBytes("UTF-8"), "ISO8859-1");
        } else if (request.getHeader("User-Agent").toUpperCase()
                .indexOf("MSIE") > 0) {
            // IE浏览器
            filename = URLEncoder.encode(filename, "UTF-8");
        }
        response.setHeader("Content-Disposition", "attachment;filename="
                + filename);
        // 从服务器上 读取文件
        String path = "C://"+filename;
        File file = new File(path);
        response.setContentLength(Integer.valueOf(((Long) file.length())
                .toString()));
        // 输入流 读取目标文件
        FileInputStream fis = new FileInputStream(file);
        int len ;
        byte[] data = new byte[1024];
        ByteOutputStream bos = new ByteOutputStream(1024);
        // 文件读到最末尾 返回 -1
        while ((len = fis.read(data)) != -1) {
            // 将服务器中的数据 转换成二进制数组 放入内存中
            bos.write(data, 0, len);
        }
        // 将服务器上的文件转换成 二进制数组 OutPutStream 输出流 写入对应文件中
        OutputStream os = response.getOutputStream();
        // 从服务器 拿到数据 向客户端进行写入
        os.write(bos.getBytes());
        // 清空内存文件 向客户端写入
        os.flush();
        // 关闭输出流
        os.close();
        // 关闭输入流
        fis.close();
        bos.close();
    }

}
