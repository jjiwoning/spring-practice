package com.example.springpractice.interceptor;

import com.example.springpractice.auth.JwtUtil;
import com.example.springpractice.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenInterceptor implements HandlerInterceptor {

    private final JwtUtil jwtUtil;

    private final UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String accessToken = request.getHeader(JwtUtil.ACCESS_TOKEN_NAME);

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
}
