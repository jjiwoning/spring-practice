package com.example.springpractice.member.interceptor;

import java.util.Objects;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.example.springpractice.member.auth.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenInterceptor implements HandlerInterceptor {

    private final JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String accessToken = request.getHeader(JwtUtil.ACCESS_TOKEN_NAME);

        if (isPreflightRequest(request)) {
            // cors 검증 요청인 경우 인터셉터 바로 통과하게 설정해줘야 cors 이슈가 발생하지 않는다.
            return true;
        }

        // accessToken이 유효한 경우
        if (jwtUtil.checkToken(accessToken)) {
            // 토큰 성공 로직 실행
            // request에 값 세팅하기
            return true;
        }

        String refreshToken = request.getHeader(JwtUtil.REFRESH_TOKEN_NAME);

        // refreshToken이 유효한 경우
        if (jwtUtil.checkToken(refreshToken)) {
            // 새로운 accessToken 생성 로직 넣어주기 + 현재 로그인 유저 정보와 유저 엔티티의 refreshToken 정보 비교해서 검증
            return true;
        }

        // 모든 경우가 실패 -> 401 보내기
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        return false;
    }

    private boolean isPreflightRequest(HttpServletRequest request) {
        return isOptions(request) && hasHeaders(request) && hasMethod(request) && hasOrigin(request);
    }

    private boolean isOptions(HttpServletRequest request) {
        return request.getMethod().equalsIgnoreCase(HttpMethod.OPTIONS.toString());
    }

    private boolean hasHeaders(HttpServletRequest request) {
        return Objects.nonNull(request.getHeader("Access-Control-Request-Headers"));
    }

    private boolean hasMethod(HttpServletRequest request) {
        return Objects.nonNull(request.getHeader("Access-Control-Request-Method"));
    }

    private boolean hasOrigin(HttpServletRequest request) {
        return Objects.nonNull(request.getHeader("Origin"));
    }
}
