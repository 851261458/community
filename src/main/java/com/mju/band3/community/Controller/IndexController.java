package com.mju.band3.community.Controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mju.band3.community.Controller.cache.HotTageCache;
import com.mju.band3.community.DTO.PageDTO;
import com.mju.band3.community.DTO.QuestionDTO;
import com.mju.band3.community.Mapper.QuestionMapper;
import com.mju.band3.community.Mapper.UserMapper;
import com.mju.band3.community.Model.Question;
import com.mju.band3.community.Model.User;
import com.mju.band3.community.Service.QuestionService;
import com.mju.band3.community.schedule.HotTagTasks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;


@Controller
public class IndexController {
    @Autowired
    UserMapper userMapper;
    @Autowired
    QuestionService questionService;
    @Autowired
    HotTageCache hotTageCache;
    @GetMapping(value = "/")
    public String Index( Model model,
                        @RequestParam(name = "page",defaultValue = "1")Integer page,
                        @RequestParam(name = "size",defaultValue = "10")Integer size){

        PageHelper.startPage(page,size);
        List<QuestionDTO> questionDTOS = questionService.selectAll();
        PageInfo<QuestionDTO> pageInfo=new PageInfo(questionDTOS,5);
        List<String> sort = hotTageCache.getSort();
        List<String> tags;
        if (sort.size()>10){
            tags=sort.subList(0,10);
        }else {
            tags=sort;
        }
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("tags",tags);
        return "index";
    }
}
