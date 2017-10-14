package www.lvmama.com.controller;

import io.swagger.annotations.Api;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Administrator on 2017/7/21.
 */

@Controller
public class FileUpload {

    @RequestMapping("/index")
    public String StrihelloHtml(){
        return "/index";
    }

    @RequestMapping("/upload")
    public void upload(@RequestParam(value = "file") MultipartFile multipartFile, HttpServletRequest request, HttpServletResponse response){

        String path = request.getServletContext().getRealPath("/image");
        String pathb = request.getSession().getServletContext().getRealPath("/image");

        File file = new File(path+"/"+multipartFile.getOriginalFilename());
        System.out.println(path);
        System.out.println(pathb);
        try {
            FileUtils.copyInputStreamToFile(multipartFile.getInputStream(),file);
            printlnJson("dd",response);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        printlnJson("dd",response);
    }

    public void printlnJson(String msg, HttpServletResponse response) {
        PrintWriter writer = null;
        response.reset();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        try {
            writer = response.getWriter();
            writer.println(msg);
            writer.flush();
        } catch (IOException e) {
          e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
