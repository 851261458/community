package com.mju.band3.community.schedule;

import com.mju.band3.community.Controller.cache.HotTageCache;
import com.mju.band3.community.Mapper.QuestionMapper;
import com.mju.band3.community.Model.Question;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Slf4j
public class HotTagTasks {
    @Autowired
    QuestionMapper questionMapper;
    @Autowired
    HotTageCache hotTageCache;
    @Scheduled(fixedRate = 1000*60*60*3)
    public void reportCurrentTime(){
        int offset=0;
        int limit=5;
        List<Question> list=new ArrayList<>();
        Map<String, Integer> priorities = new HashMap<>();
        log.info("Beginning The time is now {}",new Date());
        while (offset==0||list.size()==limit){
            list=questionMapper.selectWithRowsbounds(offset,limit);
            for (Question question : list) {
                String[] tags = StringUtils.split(question.getTag(), ",");
                for (String tag : tags) {
                    Integer priority = priorities.get(tag);
                    if (priority != null) {
                        priorities.put(tag, priority + 5 + question.getCommentCount());
                    } else {
                        priorities.put(tag, 5 + question.getCommentCount());
                    }
                }
            }
            offset+=limit;
        }
        hotTageCache.setTag(priorities);
        hotTageCache.Sort(hotTageCache.getTag());
        log.info("Ending The time is now {}",new Date());
    }


}
