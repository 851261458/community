package com.mju.band3.community.Controller;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mju.band3.community.DTO.PageDTO;
import com.mju.band3.community.DTO.QuestionDTO;
import com.mju.band3.community.Model.User;
import com.mju.band3.community.Service.NotificationService;
import com.mju.band3.community.Service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by codedrinker on 2019/5/15.
 */
@Controller
public class ProfileController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private NotificationService notificationService;

    @GetMapping("/profile/{action}")
    public String profile(HttpServletRequest request,
                          @PathVariable(name = "action")String action,Model model,
                          @RequestParam(name = "page", defaultValue = "1") Integer page,
                          @RequestParam(name = "size", defaultValue = "5") Integer size) {

        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }

        if ("questions".equals(action)) {
            model.addAttribute("section", "questions");
            model.addAttribute("sectionName", "我的提问");
            PageHelper.startPage(page,size);
            List<QuestionDTO> list = questionService.list(user.getAccountId());
            PageInfo pageInfo=new PageInfo(list,5);
            model.addAttribute("pageInfo",pageInfo);
        } else if ("replies".equals(action)) {

//            model.addAttribute("section", "replies");
//            model.addAttribute("pagination", paginationDTO);
//            model.addAttribute("sectionName", "最新回复");
        }

        return "profile";
    }
}