package com.xiaojian.community.controller;

import com.xiaojian.community.dto.AccessTokenDTO;
import com.xiaojian.community.dto.GithubUser;
import com.xiaojian.community.provider.GitHubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * GitHub授权
 */
@Controller
public class AuthorizeContorller {
    @Autowired
    private GitHubProvider gitHubProvider;
    @GetMapping("/callback")
    public String calBack(@RequestParam(name="code") String code, @RequestParam(name="state") String state){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        accessTokenDTO.setRedirect_uri("http://localhost:8887/callback");
        accessTokenDTO.setClient_id("41906e95e453e7a05abb");
        accessTokenDTO.setClient_secret("eb108d62c6c13713455f1717cd418c92fb73ae96");
        String token = gitHubProvider.getAccessToken(accessTokenDTO);
        System.out.println(token);
        GithubUser githubUser = gitHubProvider.getUser(token);
        System.out.println(githubUser.getName());
        return "index";
    }

}
