package com.me.socialoauthtest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

@Controller
public class controller {

    @GetMapping("/")
    public String home(){
        return "main";
    }


    @GetMapping("/login")
    public String login(){
        return "login";
    }

    // code요청->발급, 토큰발급요청->발급, 정보요청->응답 하여 카카오id(번호)와, email
    @GetMapping("/oauth/kakao")
    public String kakaoCallBack(String code) {

        //post방식으로 key=value 데이터를 요청(카카오한테)
        RestTemplate rt = new RestTemplate(); // 라이브러리

        //HttpHeader 오브젝트 생성
        HttpHeaders headers= new HttpHeaders();
        headers.add("Content-type","application/x-www-form-urlencoded;charset=utf-8");

        //HttpBody 오브젝트 생성
        MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "ac8fe049ee6f6daabf4f2c47b6565538");
        params.add("redirect_uri", "http://localhost:8080/oauth/kakao");
        params.add("code", code);

        //HttpHeader와 HttpBody를 하나의 오브젝트에 담기
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
                new HttpEntity<>(params, headers);

        //Http 요청하기 - Post방식으로 그리고 response 변수의 응답받음.
        ResponseEntity<String> response =rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );


        //Gson, Json Simple, ObjectMapper
        //ObjectMapper 로 json데이터 처리
        ObjectMapper objectMapper = new ObjectMapper();
        OAuthToken oauthToken = null;
        try {
            oauthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
        } catch (JsonMappingException e) {

            e.printStackTrace();
        } catch (JsonProcessingException e) {

            e.printStackTrace();
        }
        System.out.println("카카오 액세스 토큰 : " + oauthToken.getAccess_token());



        RestTemplate rt2 = new RestTemplate();

        // HttpHeader 오브젝트 생성
        HttpHeaders headers2 = new HttpHeaders();
        headers2.add("Authorization", "Bearer "+oauthToken.getAccess_token());
        headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        // HttpHeader와 HttpBody를 하나의 오브젝트에 담기
        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest2 = new HttpEntity<>(headers2);

        // Http 요청하기 - Post방식으로 - 그리고 response 변수의 응답 받음.
        ResponseEntity<String> response2 = rt2.exchange(
                "https://kapi.kakao.com/v2/user/me",  // 토큰발급주소
                HttpMethod.POST,
                kakaoProfileRequest2,
                String.class); // String.class는 String 자체를 가리킨다.
        // ex


        ObjectMapper objectMapper2 = new ObjectMapper();
        KakaoProfile KakaoProfile = null;
        try {
            KakaoProfile = objectMapper2.readValue(response2.getBody(), KakaoProfile.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println("카카오아이디(번호) : "+KakaoProfile.getId() + " 카카오 이메일 :" + KakaoProfile.getKakao_account().getEmail());

        return "succ";
    }
    }
