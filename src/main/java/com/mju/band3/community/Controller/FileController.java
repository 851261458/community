package com.mju.band3.community.Controller;

import com.mju.band3.community.DTO.FileDTO;
import com.mju.band3.community.Provider.UcloudProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.codec.multipart.MultipartHttpMessageReader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@ResponseBody
@Controller
public class FileController {

    @Autowired
    UcloudProvider ucloudProvider;
    @RequestMapping(value = "/file/upload")
    public FileDTO Upload(HttpServletRequest request){
        MultipartHttpServletRequest multipartRequest= (MultipartHttpServletRequest) request;
        MultipartFile file = multipartRequest.getFile("editormd-image-file");
        try {
            String fileName = ucloudProvider.upload(file.getInputStream(), file.getContentType(), file.getOriginalFilename());
            FileDTO fileDTO = new FileDTO();
            fileDTO.setSuccess(1);
            fileDTO.setUrl(fileName);
            return fileDTO;
        } catch (IOException e) {
//            log.error("upload error", e);
            FileDTO fileDTO = new FileDTO();
            fileDTO.setSuccess(0);
            fileDTO.setMessage("上传失败");
            return fileDTO;
        }

    }
}
