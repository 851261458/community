package com.mju.band3.community.Service;

import com.mju.band3.community.Mapper.NotificationMapper;
import com.mju.band3.community.Model.Notification;
import com.mju.band3.community.Model.NotificationExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    @Autowired
    NotificationMapper notificationMapper;

    public List<Notification> selectReceiveMsgWithoutYourself(String accountId) {
        NotificationExample example = new NotificationExample();
        example.createCriteria().andReceiverEqualTo(Long.valueOf(accountId))
                .andNotifierNotEqualTo(Long.valueOf(accountId));
        example.setOrderByClause("gmt_create desc");

        List<Notification> notificationList = notificationMapper.selectByExample(example);
        return notificationList;
    }

    public Notification changeStatus(Long id) {
      notificationMapper.updateByPrimaryKeySelective(new Notification(id,1));
        Notification notification = notificationMapper.selectByPrimaryKey(id);
        return notification;

    }
}
