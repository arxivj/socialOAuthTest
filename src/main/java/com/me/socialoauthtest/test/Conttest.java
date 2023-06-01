package com.me.socialoauthtest.test;

import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class Conttest {

    @GetMapping(value = "/tokenCreate2/{email}")
    public JSONObject createToken(@PathVariable String email) throws Exception {

        JwtUtil jwtUtil = new JwtUtil();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", jwtUtil.createAccessToken(email));

        JSONObject tokenResponse = new JSONObject();





        tokenResponse.put("headers", headers);

        System.out.println("토큰 확인 : " + tokenResponse);
        return tokenResponse;
    }
}

