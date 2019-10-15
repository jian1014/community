package com.xiaojian.community.controller;

import com.xiaojian.community.dto.AccessTokenDTO;
import com.xiaojian.community.dto.GithubUser;
import com.xiaojian.community.provider.GitHubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    @Value("${github.Client_id}")
    private String clientId;
    @Value("${github.setRedirect_uri}")
    private String uri;
    @Value("${github.Client_secret}")
    private String clientSecret;

    @GetMapping("/callback")
    public String calBack(@RequestParam(name="code") String code, @RequestParam(name="state") String state){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        accessTokenDTO.setRedirect_uri(uri);
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        String token = gitHubProvider.getAccessToken(accessTokenDTO);
        System.out.println(token);
        GithubUser githubUser = gitHubProvider.getUser(token);
        System.out.println(githubUser.getName());
        return "index";
    }

}
