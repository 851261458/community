package com.mju.band3.community.Controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mju.band3.community.DTO.QuestionDTO;
import com.mju.band3.community.Service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class SearchController {
    @Autowired
    QuestionService questionService;

    @GetMapping(value = "/search")
    public String search(@RequestParam(name = "search")String search, Model model,
                         @RequestParam(name = "page",defaultValue = "1")Integer page
    ){
        PageHelper.startPage(page,5);
        List<QuestionDTO> search1 = questionService.search(search);
        System.out.println(search1);
        PageInfo pageInfo=new PageInfo(search1,5);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("search",search);
        return "search";
    }
}
