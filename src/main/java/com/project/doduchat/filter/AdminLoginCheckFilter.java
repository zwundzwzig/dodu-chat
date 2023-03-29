package com.project.doduchat.filter;

import com.project.doduchat.utils.SessionConst;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.util.PatternMatchUtils;

import java.io.IOException;

public class AdminLoginCheckFilter implements Filter {
    // Security에서 통과되었더라도 Filter에서 걸릴 가능성이 있으므로 admin 제외한 부분들도 whitelist에 추가
    private static final String[] whitelist = {"/", "/doduLogin", "/admin", "/admin/home", "/admin/login", "/admin/logout" // admin 로그인 없이 접근
            ,"/chatgpt", "/applyForm/*" , "/mentee/applyList/*" 
            ,"/api/v1/**", "/mentor/**", "/mentee/**", "/chat/**", "/chatList"
            ,"/css/*", "/images/*", "/js/*", "/layout/*", "/ws/chat"
    };

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        try {
            System.out.println("인증 체크 필터 시작:" + requestURI);
            if (isLoginCheckPath(requestURI)) { // false인 경우
                System.out.println("인증 체크 로직 실행: " + requestURI);
                HttpSession session = httpRequest.getSession(false);
                // check test
                if (session != null) {
                    System.out.println("getSession TEST :: " + session.getAttribute(SessionConst.LOGIN_ADMIN));
                }
                // ===========
                if (session == null || session.getAttribute(SessionConst.LOGIN_ADMIN) == null) {
                    System.out.println("미인증 사용자 요청:" + requestURI);
                    // 로그인으로 redirect
                    httpResponse.sendRedirect("/admin");
                    return; // 여기가 중요, 미인증 사용자는 다음으로 진행하지 않고 끝!
                }
            }
            chain.doFilter(request, response);
        } catch (Exception e) {
            throw e;
        } finally {
            System.out.println("인증 체크 필터 종료" +  requestURI);
        }
    }

    /**
     * 화이트 리스트의 경우 인증 체크X
     */
    private boolean isLoginCheckPath(String requestURI) {
        return !PatternMatchUtils.simpleMatch(whitelist, requestURI);
    }
}