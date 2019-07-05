package life.majiang.community.controller;

import life.majiang.community.dto.AccessTokenDTO;
import life.majiang.community.dto.GithubUser;
import life.majiang.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider   githubProvider;

    @Value("${github.client.id}")
    private  String clientId;
    @Value("${github.redirect.uri}")
    private  String  redirectUri;
    @Value("${github.client.secret}")
    private  String  clientSecret;

    @GetMapping("/callback")
     public    String   callback(@RequestParam(name="code")String  code,@RequestParam(name="state")String  state){
         //使用Java模拟post请求  ctrl + alt + v  这些地址写到配置文件中  不可以动态修改   使用配置文件来代替
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
       // accessTokenDTO.setRedirect_uri("http://localhost:8081/callback");
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
       // accessTokenDTO.setClient_id("Iv1.f6a1e555a3ec137a");
        accessTokenDTO.setClient_id(clientId);
        //accessTokenDTO.setClient_secret("8abad5560c4cc493530f316eca0f918140cf9482");
        accessTokenDTO.setClient_secret(clientSecret);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser user = githubProvider.getUser(accessToken);
        System.out.println(user.getLogin());
        System.out.println(user);
        return  "index";
     }
}
