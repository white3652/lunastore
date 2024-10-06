package com.lunastore.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HttpSession session = request.getSession(false);

        boolean isBuyerLoggedIn = false;
        boolean isSellerLoggedIn = false;

        if (session != null) {
            isBuyerLoggedIn = session.getAttribute("buyer") != null;
            isSellerLoggedIn = session.getAttribute("seller") != null;
        }

        String requestURI = request.getRequestURI();
        String method = request.getMethod();

        if (
                requestURI.startsWith("/buyer/login") ||
                        requestURI.startsWith("/buyer/buyerLoginProcess") ||
                        requestURI.startsWith("/buyer/join") ||
                        requestURI.startsWith("/buyer/buyerJoinProcess") ||
                        requestURI.startsWith("/buyer/findPw") ||
                        requestURI.startsWith("/buyer/findPwProcess") ||
                        requestURI.startsWith("/buyer/verifyEmail") ||
                        requestURI.startsWith("/buyer/buyerVerifyEmail") ||
                        requestURI.startsWith("/seller/sellerLogin") ||
                        requestURI.startsWith("/seller/sellerLoginProcess") ||
                        requestURI.startsWith("/seller/sellerJoin") ||
                        requestURI.startsWith("/seller/sellerJoinProcess") ||
                        requestURI.startsWith("/seller/findPw") ||
                        requestURI.startsWith("/seller/findPwProcess") ||
                        requestURI.startsWith("/seller/sellerVerifyEmail") ||
                        requestURI.startsWith("/seller/sellerVerifyEmailProcess") ||
                        requestURI.startsWith("/api/") || // API 경로는 별도로 처리
                        requestURI.startsWith("/tui-editor/") ||
                        requestURI.startsWith("/resources/") || // 정적 리소스 경로
                        requestURI.startsWith("/static/")
        ) {
            return true;
        }

        if (requestURI.startsWith("/api/")) {
            if (isBuyerLoggedIn || isSellerLoggedIn) {
                return true;
            } else {
                log.warn("Unauthorized API access attempt to: " + requestURI);
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("로그인이 필요합니다.");
                return false;
            }
        }

        if (requestURI.startsWith("/buyer/")) {
            if (isBuyerLoggedIn) {
                return true;
            } else {
                log.warn("Unauthorized access attempt to: " + requestURI);
                response.sendRedirect(request.getContextPath() + "/buyer/login");
                return false;
            }
        }

        if (requestURI.startsWith("/seller/")) {
            if (isSellerLoggedIn) {
                return true;
            } else {
                log.warn("Unauthorized access attempt to: " + requestURI);
                response.sendRedirect(request.getContextPath() + "/seller/sellerLogin");
                return false;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, org.springframework.web.servlet.ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}