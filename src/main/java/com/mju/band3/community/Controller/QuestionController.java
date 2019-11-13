package com.mju.band3.community.Controller;
import com.mju.band3.community.DTO.CommentReviewDTO;
import com.mju.band3.community.DTO.QuestionDTO;
import com.mju.band3.community.Service.CommentService;
import com.mju.band3.community.Service.QuestionService;
import com.mju.band3.community.enums.CommentTypeEnum;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
public class QuestionController {
    @Autowired
    QuestionService questionService;
    @Autowired
    CommentService commentService;
    @GetMapping(value = "/question/{id}")
    public String  question(@PathVariable(name = "id")Integer id,
                            @RequestParam(name = "pn",required = false,defaultValue = "1") Integer pn, Model model){
        QuestionDTO questionDTO=questionService.getById(id);
            QuestionDTO questionDTO1=new QuestionDTO();
        BeanUtils.copyProperties(questionDTO,questionDTO1);
        questionService.incrView(id);
        List<QuestionDTO> relatedQuestions = questionService.selectRelated(questionDTO1);
        List<CommentReviewDTO> comments=commentService.listByTargetId(id, CommentTypeEnum.QUESTION.getType());
        model.addAttribute("comments",comments);
        model.addAttribute("question",questionDTO);
        model.addAttribute("relatedQuestions",relatedQuestions);
        return "question";
    }

}
