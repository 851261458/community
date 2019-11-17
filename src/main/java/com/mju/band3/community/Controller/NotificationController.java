package com.mju.band3.community.Controller;

import com.mju.band3.community.Model.Notification;
import com.mju.band3.community.Model.User;
import com.mju.band3.community.Service.CommentService;
import com.mju.band3.community.Service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

@Controller
public class NotificationController {

    @Autowired
    private NotificationService notificationService;
    @Autowired
    CommentService commentService;

    @GetMapping("/notification/{id}")
    public String profile(HttpServletRequest request,
                          @PathVariable(name = "id") Long id) {

        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }
        Notification notification = notificationService.changeStatus(id);
        if (notification.getType()==1){
            return "redirect:/question/"+notification.getOuterid();
        }else{
            Long aLong = commentService.selectParentIdByOutId(notification.getOuterid());
            return "redirect:/question/"+aLong;
        }
    }

}