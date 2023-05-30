package com.me.socialoauthtest.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Base64;
import java.util.Date;


//두 번째 코드는 JWT를 생성하고 검증하는 로직을 담당하는 JwtProvider 클래스입니다.
// createToken(String subject) 메서드는 subject를 입력으로 받아 JWT를 생성합니다.
// parseJwtToken(String token) 메서드는 토큰을 입력으로 받아 이를 검증하고, JWT의 클레임(claim)들을 반환합니다.
// BearerRemove(String token) 메서드는 "Bearer " 문자열을 토큰에서 제거하는 역할을 합니다. JWT를 사용할 때 일반적으로 "Bearer"라는 단어가 앞에 붙는데, 이 메서드를 통해 실제 토큰 부분만을 추출할 수 있습니다.

@Component
public class JwtProvider {

    @Value("${jwt.password}")
    private String secretKey;

    //==토큰 생성 메소드==//
    public String createToken(String subject) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + Duration.ofDays(1).toMillis()); // 만료기간 1일

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE) // (1)
                .setIssuer("test") // 토큰발급자(iss)
                .setIssuedAt(now) // 발급시간(iat)
                .setExpiration(expiration) // 만료시간(exp)
                .setSubject(subject) //  토큰 제목(subject)
                .signWith(SignatureAlgorithm.HS256, Base64.getEncoder().encodeToString(secretKey.getBytes())) // 알고리즘, 시크릿 키
                .compact();
    }

    //==Jwt 토큰의 유효성 체크 메소드==//
    public Claims parseJwtToken(String token) {
        token = BearerRemove(token); // Bearer 제거
        return Jwts.parser()
                .setSigningKey(Base64.getEncoder().encodeToString(secretKey.getBytes()))
                .parseClaimsJws(token)
                .getBody();
    }

    //==토큰 앞 부분('Bearer') 제거 메소드==//
    private String BearerRemove(String token) {
        return token.substring("Bearer ".length());
    }
}