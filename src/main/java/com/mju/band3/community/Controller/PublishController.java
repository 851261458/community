package com.mju.band3.community.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PublishController {
    @GetMapping(value = "/publish")
    public String toPublish(){
        return "publish";
    }
}
