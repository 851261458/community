package com.mju.band3.community.Controller;

import com.mju.band3.community.Entity.AccessTokenDTO;
import com.mju.band3.community.Entity.GitHupUser;
import com.mju.band3.community.Provider.GitHupProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping(value = "/callback")
    public String callBack(@RequestParam(name ="code")String code,
                           @RequestParam(name="state")String state){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id("84f481e2429bb9deb1c3");
        accessTokenDTO.setClient_secret("d0e1b4793c812438e202b2aad4c4f500ce5b8c71");
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        accessTokenDTO.setRedirect_uri("http://localhost:8080/callback");
        String accessToken = gitHupProvider.getAccessToken(accessTokenDTO);
        GitHupUser user = gitHupProvider.getUser(accessToken);
        System.out.println(user);
        return "index";

    }

}
