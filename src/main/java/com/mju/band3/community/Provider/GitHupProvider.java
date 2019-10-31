package com.mju.band3.community.Provider;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mju.band3.community.Entity.AccessTokenDTO;
import com.mju.band3.community.Entity.GitHupUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GitHupProvider {


    /*
        通过利用OKHTTP携带相关参数发送POST请求到access_token地址
        获取access_token的值
     */
    public String getAccessToken(AccessTokenDTO accessTokenDTO) {

        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            String token = string.split("&")[0].split("=")[1];
            return token;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    /*
    通过OKHTTP发送GET请求携带access_token的值来获取user（GitHupUser）的值。
     */
    public GitHupUser getUser(String accessToken){
        OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("https://api.github.com/user?access_token="+accessToken)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                String string = response.body().string();
                GitHupUser gitHupUser = JSON.parseObject(string, GitHupUser.class);
                return gitHupUser;

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
    }



    }


