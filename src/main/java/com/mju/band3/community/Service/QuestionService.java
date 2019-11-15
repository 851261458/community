package com.mju.band3.community.Service;
import com.mju.band3.community.DTO.QuestionDTO;
import com.mju.band3.community.Mapper.QuestionMapper;
import com.mju.band3.community.Mapper.UserMapper;
import com.mju.band3.community.Model.Question;
import com.mju.band3.community.Model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    UserMapper userMapper;

    public  void InsertOrUpdare(Question question) {
        if (question.getId()==null){
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.AskQuestion(question);
        }else{
            question.setGmtModified(System.currentTimeMillis());
            questionMapper.update(question);
        }

    }

    public  List<QuestionDTO> selectAll() {
        List<QuestionDTO> questions = questionMapper.selectAll();
       return questions;
    }

    public List<QuestionDTO> list(String accountId) {
        return questionMapper.list(Integer.valueOf(accountId));
    }

    public QuestionDTO getById(Integer id) {
        Question byId = questionMapper.getById(id);
        User byAccount_id = userMapper.findByAccount_Id(byId.getCreator());
        QuestionDTO questionDTO=new QuestionDTO();
        questionDTO.setUser(byAccount_id);
        BeanUtils.copyProperties(byId,questionDTO);
        return questionDTO;
    }

    public void incrView(Integer id) {
        questionMapper.updateViewCount(id);
    }

    public List<QuestionDTO> selectRelated(QuestionDTO questionDTO) {
        if (StringUtils.isEmpty(questionDTO.getTag())){
            return new ArrayList<>();
        }
        String replace = questionDTO.getTag().replace(',', '|');
        questionDTO.setTag(replace);
        return questionMapper.selectRelated(questionDTO);
    }

    public List<QuestionDTO> search(String search) {
        return questionMapper.search(search);
    }

//    public  PageDTO selectAll(Integer page, Integer size) {
//        Integer totalCount = questionMapper.count();
//        Integer totalPage;
//        if (totalCount%size==0){
//            totalPage=totalCount/size;
//        }else{
//            totalPage=totalCount/size+1;
//        }
//        if (page<0){
//            page=1;
//        }
//        if (page>totalPage){
//            page=totalPage;
//        }
//        Integer offset=size*(page-1);
//        List<Question> questions = questionMapper.selectAll(offset,size);
//        List<QuestionDTO> questionDTOS=new ArrayList<>();
//        PageDTO pageDTO=new PageDTO();
//        for (Question question : questions) {
//            User user=userMapper.findById(question.getCreator());
//            QuestionDTO questionDTO=new QuestionDTO();
//            questionDTO.setUser(user);
//            BeanUtils.copyProperties(question,questionDTO);
//            questionDTOS.add(questionDTO);
//        }
//        pageDTO.setQuestionDTOS(questionDTOS);
//
//        pageDTO.setPagination(totalCount,page,size,totalPage);
//
//        return pageDTO;
//    }


}
