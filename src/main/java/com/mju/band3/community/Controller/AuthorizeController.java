package com.mju.band3.community.Controller;

import com.mju.band3.community.Entity.AccessTokenDTO;
import com.mju.band3.community.Entity.GitHupUser;
import com.mju.band3.community.Mapper.UserMapper;
import com.mju.band3.community.Model.User;
import com.mju.band3.community.Provider.GitHupProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

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
    UserMapper userMapper;

    @GetMapping(value = "/callback")
    public String callBack(@RequestParam(name ="code")String code,
                           @RequestParam(name="state")String state,HttpServletResponse response){
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
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
            response.addCookie(new Cookie("token",token));
            return "redirect:/";
        }else {
            return "redirect:/";
        }


    }

}
