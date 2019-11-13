package com.mju.band3.community.Controller;

import com.mju.band3.community.Controller.cache.TagCache;
import com.mju.band3.community.Mapper.QuestionMapper;
import com.mju.band3.community.Model.Question;
import com.mju.band3.community.Model.User;
import com.mju.band3.community.Service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class PublishController {

    @Autowired
    QuestionMapper questionMapper;
    @Autowired
    QuestionService questionService;

    @GetMapping(value = "/publish")
    public String toPublish(Model model) {
        model.addAttribute("tags", TagCache.get());
        return "publish";
    }


    @PostMapping(value = "/publish")
    public String doPublish(Question question, HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("title",question.getTitle());
        model.addAttribute("description",question.getDescription());
        model.addAttribute("tag",question.getTag());
        model.addAttribute("tags", TagCache.get());
        if (user == null) {
            model.addAttribute("error", "用户未登录");
            return "publish";
        }
        if (StringUtils.isEmpty(question.getTitle())){
            model.addAttribute("error", "标题不能为空");
            return "publish";
        }
        if (StringUtils.isEmpty(question.getDescription())){
            model.addAttribute("error", "请填写问题补充来细节描述你的问题");
            return "publish";
        }
        if (StringUtils.isEmpty(question.getTag())){
            model.addAttribute("error", "标签不能为空");
            return "publish";
        }
        String invalid = TagCache.filterInvalid(question.getTag());
        if (!StringUtils.isEmpty(invalid)) {
            model.addAttribute("error", "输入非法标签:" + invalid);
            return "publish";
        }

        question.setCreator(Integer.valueOf(user.getAccountId()));

        questionService.InsertOrUpdare(question);


        return "redirect:/";
    }

    @GetMapping(value = "/publish/{id}")
    public String toedit(@PathVariable("id")Integer id,Model model){
        Question question = questionMapper.getById(id);
        model.addAttribute("title",question.getTitle());
        model.addAttribute("description",question.getDescription());
        model.addAttribute("tag",question.getTag());
        model.addAttribute("id",question.getId());
        model.addAttribute("tags", TagCache.get());
        return "publish";

    }
}
