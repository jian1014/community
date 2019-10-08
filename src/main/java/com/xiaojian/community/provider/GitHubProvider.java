package com.xiaojian.community.provider;

import com.alibaba.fastjson.JSON;
import com.xiaojian.community.dto.AccessTokenDTO;
import com.xiaojian.community.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @Component注解和@Controller的区别为@Controller是有路由功能，@Component注解的作用仅仅是将这个类初始化到spring的上下文
 * 作用：通过OkHttp用post的方式来访问GitHub获取code
 */
@Component
public class GitHubProvider {
    public String getAccessToken(AccessTokenDTO accessTokenDTO){
       MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                    .url("https://github.com/login/oauth/access_token")
                    .post(body)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                String string = response.body().string();
                String[] split = string.split("&");
                String token =  split[0].substring(split[0].indexOf("=")+1);
                return token;
            }catch (IOException e) {
                e.printStackTrace();
            }
        return "";
    }

    public GithubUser getUser(String accessToken){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+accessToken)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            return JSON.parseObject(string,GithubUser.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
