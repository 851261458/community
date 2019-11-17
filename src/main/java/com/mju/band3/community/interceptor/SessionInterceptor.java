package com.mju.band3.community.interceptor;

import com.mju.band3.community.Mapper.NotificationMapper;
import com.mju.band3.community.Mapper.UserMapper;
import com.mju.band3.community.Model.NotificationExample;
import com.mju.band3.community.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@Component
public class SessionInterceptor implements HandlerInterceptor {
    @Autowired
    UserMapper userMapper;
    @Autowired
    NotificationMapper notificationMapper;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();
        if (cookies!=null&&cookies.length!=0){
            for (Cookie cookie : cookies) {
                if ("token".equals(cookie.getName())){
                    String token = cookie.getValue();
                    User user=userMapper.findByToken(token);
                    if (user!=null){
                        request.getSession().setAttribute("user",user);
                        NotificationExample example = new NotificationExample();
                        example.createCriteria()
                                .andReceiverEqualTo(Long.valueOf(user.getAccountId()))
                                .andNotifierNotEqualTo(Long.valueOf(user.getAccountId()))
                                .andStatusEqualTo(0);
                        long l = notificationMapper.countByExample(example);
                        request.getSession().setAttribute("UnRead",l);
                    }
                    break;
                }
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
