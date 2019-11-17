package com.mju.band3.community.Controller;

import com.mju.band3.community.DTO.AccessTokenDTO;
import com.mju.band3.community.DTO.GitHupUser;
import com.mju.band3.community.Mapper.UserMapper;
import com.mju.band3.community.Model.User;
import com.mju.band3.community.Provider.GitHupProvider;
import com.mju.band3.community.Service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;
@Slf4j
@Controller
public class AuthorizeController {
    @Value("${githup.setClient.id}")
    String setClientId;
    @Value("${githup.setClient.secret}")
    String setClientSecret;
    @Value("${githup.setRedirect.uri}")
    String setRedirectUri;
    @Autowired
    GitHupProvider gitHupProvider;
    @Autowired
    UserService userService;

    @GetMapping(value = "/callback")
    public String callBack(@RequestParam(name ="code")String code,
                           @RequestParam(name="state")String state, HttpServletResponse response){
        System.out.println(code);
        System.out.println(state);
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(setClientId);
        accessTokenDTO.setClient_secret(setClientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        accessTokenDTO.setRedirect_uri(setRedirectUri);
        String accessToken = gitHupProvider.getAccessToken(accessTokenDTO);
        GitHupUser gitHupUser = gitHupProvider.getUser(accessToken);
        if (gitHupUser!=null){
            User user=new User();
            String token=UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(gitHupUser.getName());
            user.setAccountId(String.valueOf(gitHupUser.getId()));
            user.setAvatarUrl(gitHupUser.getAvatarUrl());
            userService.insertOrUpdate(user);
            Cookie cookie = new Cookie("token",token);
            cookie.setMaxAge(60*60*24*7);
            response.addCookie(cookie);
            return "redirect:/";
        }else {
            log.error("AuthorizeController-callBack-gitHupUser-null ,{}",gitHupUser);
            return "redirect:/";
        }


    }


    @GetMapping(value = "/logout")
    public String logout(HttpServletRequest request,HttpServletResponse response){
        request.getSession().removeAttribute("user");
        Cookie cookie=new Cookie("token",null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return "redirect:/";
    }



}
