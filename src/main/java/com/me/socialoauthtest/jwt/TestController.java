package com.me.socialoauthtest.jwt;

import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {

// JWT를 생성하고 검증하는 컨트롤러를 나타냅니다.
// tokenCreate/{userId} 경로로 들어오는 요청을 처리하는 컨트롤러에서는 JWT를 생성하고, 이를 검증합니다. 생성된 토큰은 사용자의 응답으로 보내집니다.
// checkToken 경로로 들어오는 요청을 처리하는 컨트롤러에서는 요청 헤더의 "Authorization" 필드에 담긴 토큰을 검증합니다.

    private final JwtProvider jwtProvider;

    //==토큰 생성 컨트롤러==//
    @GetMapping(value = "/tokenCreate/{userId}")
    public TokenResponse createToken(@PathVariable("userId") String userId) throws Exception {
        System.out.println("도달확인");
        String token = jwtProvider.createToken(userId); // 토큰 생성
        Claims claims = jwtProvider.parseJwtToken("Bearer "+ token); // 토큰 검증

        System.out.println("토큰 생성 : " + token);
        System.out.println("토큰 검증 : " + claims);

        TokenDataResponse tokenDataResponse = new TokenDataResponse(token, claims.getSubject(), claims.getIssuedAt().toString(), claims.getExpiration().toString());
        TokenResponse tokenResponse = new TokenResponse("200", "OK", tokenDataResponse);

        return tokenResponse;
    }

    //==토큰 인증 컨트롤러==//
    @GetMapping(value = "/checkToken")
    public TokenResponseNoData checkToken(@RequestHeader(value = "Authorization") String token) throws Exception {
        Claims claims = jwtProvider.parseJwtToken(token);

        TokenResponseNoData tokenResponseNoData = new TokenResponseNoData("200", "success");
        return tokenResponseNoData;
    }

    //==Response DTO==//
    @Data
    @AllArgsConstructor
    static class TokenResponse<T> {

        private String code;
        private String msg;
        private T data;
    }

    //==Response DTO==//
    @Data
    @AllArgsConstructor
    static class TokenResponseNoData<T> {

        private String code;
        private String msg;
    }

    //==Response DTO==//
    @Data
    @AllArgsConstructor
    static class TokenDataResponse {
        private String token;
        private String subject;
        private String issued_time;
        private String expired_time;
    }
}