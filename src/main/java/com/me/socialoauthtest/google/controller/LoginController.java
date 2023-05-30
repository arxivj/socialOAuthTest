package com.me.socialoauthtest.google.controller;

import com.me.socialoauthtest.google.dto.GoogleInfResponse;
import com.me.socialoauthtest.google.dto.GoogleRequest;
import com.me.socialoauthtest.google.dto.GoogleResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin("*") // CrossOrigin("*")을 사용한 이유는 로컬 환경에서 CORS 에러가 발생하여 선언해준 것
public class LoginController {
    @Value("${google.client.id}")
    private String googleClientId;
    @Value("${google.client.pw}")
    private String googleClientPw;

    @RequestMapping(value="/api/v1/oauth2/google", method = RequestMethod.POST)
    public String loginUrlGoogle(){
        String reqUrl = "https://accounts.google.com/o/oauth2/v2/auth?client_id=" + googleClientId
                + "&redirect_uri=http://localhost:8080/oauth/google&response_type=code&scope=email%20profile%20openid&access_type=offline";
        System.out.println("reqUrl : "+reqUrl);
        return reqUrl;
    }

// 구글 로그인 페이지에서 로그인을 하게 되면,
// 아까 redirect_uri 파라미터에 넣었던 콜백 주소로 리다이렉트 되면서 code파라미터로 authorization code를 보내준다.
    @RequestMapping(value="/oauth/google", method = RequestMethod.GET)
    public String loginGoogle(@RequestParam(value = "code") String authCode){
        RestTemplate restTemplate = new RestTemplate();
        GoogleRequest googleOAuthRequestParam = GoogleRequest
                .builder()
                .clientId(googleClientId)
                .clientSecret(googleClientPw)
                .code(authCode)
                .redirectUri("http://localhost:8080/oauth/google")
                .grantType("authorization_code").build();
        ResponseEntity<GoogleResponse> resultEntity = restTemplate.postForEntity("https://oauth2.googleapis.com/token",
                googleOAuthRequestParam, GoogleResponse.class);
        String jwtToken=resultEntity.getBody().getId_token();
        Map<String, String> map=new HashMap<>();
        map.put("id_token",jwtToken);
        ResponseEntity<GoogleInfResponse> resultEntity2 = restTemplate.postForEntity("https://oauth2.googleapis.com/tokeninfo",
                map, GoogleInfResponse.class);
        String email=resultEntity2.getBody().getEmail();
        System.out.println("받아온 이메일 : "+email);
        return email;
    }
}