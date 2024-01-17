package com.example.springpractice.member.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    public static final String ACCESS_TOKEN_NAME = "access-token";
    public static final String REFRESH_TOKEN_NAME = "refresh-token";

    private static final int ACCESS_TOKEN_EXPIRE_MIN = 1;
    private static final int REFRESH_TOKEN_EXPIRE_MIN = 2;

    // 원시적인 문자열을 키 인수로 사용하지 않고 더 안전하게 사용하는 방법
    private final Key secretKey;

    private final ObjectMapper objectMapper;

    public JwtUtil(ObjectMapper objectMapper) {
        this.secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        this.objectMapper = objectMapper;
    }

    public <T> String create(String key, T data, String subject, long expire) {
        Claims claims = Jwts.claims()
                .setSubject(subject) // 토큰 제목 설정
                .setIssuedAt(new Date()) // 토큰 생성일 설정
                .setExpiration(new Date(System.currentTimeMillis() + expire));// 토큰 만료일 설정

        claims.put(key, data);

        return Jwts.builder()
                .signWith(this.secretKey) // signature -> 이 방법으로 해야 더 안전한 키 생성이 가능
                .setHeaderParam("typ", "JWT") // header
                .setClaims(claims) // payload
                .compact();
    }

    public <T> String createAccessToken(String key, T data) {
        return create(key, data, ACCESS_TOKEN_NAME, 1000 * 60 * ACCESS_TOKEN_EXPIRE_MIN);
    }

    public <T> String createRefreshToken(String key, T data) {
        return create(key, data, REFRESH_TOKEN_NAME, 1000 * 60 * 60 * 24 * 7 * REFRESH_TOKEN_EXPIRE_MIN);
    }

    public boolean checkToken(String jwt) {
        try {
            // 여기서 토큰 유효 기간을 확인하고 만료되어있다면 알아서 예외를 던져줌
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(this.secretKey)
                    .build()
                    .parseClaimsJws(jwt)
                    .getBody();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public UserInfo parseToken(String jwt) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(this.secretKey)
                .build()
                .parseClaimsJws(jwt)
                .getBody();

        return objectMapper.convertValue(claims.get(UserConst.LOGIN_TOKEN), UserInfo.class);
    }
}
