package life.majiang.community.provider;


import com.alibaba.fastjson.JSON;
import life.majiang.community.dto.AccessTokenDTO;
import life.majiang.community.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component//仅仅把当前的类初始到spring上下文当中  对象自动化放到一个池子当中  用的时候就去拿
public class GithubProvider {

    public  String   getAccessToken(AccessTokenDTO accessTokenDTO){


        MediaType    mediaType = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String  str = response.body().string();
            String access_token = str.split("&")[0].split("=")[1];
            System.out.println(str);
            return access_token;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public  GithubUser    getUser(String  access_token){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+access_token)
                .build();

        try {
            Response response = client.newCall(request).execute();
            String  str  = response.body().string();
            GithubUser githubUser = JSON.parseObject(str, GithubUser.class);
            return   githubUser;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  null;
    }
}
