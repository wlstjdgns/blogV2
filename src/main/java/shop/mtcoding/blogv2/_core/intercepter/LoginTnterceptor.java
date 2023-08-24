package shop.mtcoding.blogv2._core.intercepter;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;

import shop.mtcoding.blogv2._core.util.ApiUtil;
import shop.mtcoding.blogv2._core.util.Script;
import shop.mtcoding.blogv2.user.User;

public class LoginTnterceptor implements HandlerInterceptor {
    // 원래 인터페이스는 구체적인 메서드 못만든다. 하지만 이제 앞에 디폴트를 앞에 붙여주면 만들 수 있다.
    // 구현하고 싶은 것만 재정의해서 구현할 수 있도록.
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            @Nullable ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
        System.out.println("LoginInterceptor PostHandle");
    }
    //return true이면 컨트롤러 메서드 진입
    //return false이면 요청이 종료됨
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
                System.out.println("LoginInterceptor preHandle");

                HttpSession session = request.getSession();
                User sessionUser = (User) session.getAttribute("sessionUser");

                String startEndPoint = request.getRequestURI().split("/")[1];
                if(sessionUser == null){
                    if (startEndPoint.equals("api")) {
                         response.setHeader("Content-Type", "text/html; charset=utf-8");
                    PrintWriter out = response.getWriter();
                    ApiUtil<String> apiUtil = new ApiUtil<>(false, "인증이 필요합니다.");
                    String responseBody = new ObjectMapper().writeValueAsString(apiUtil);
                                        out.println(responseBody);

                    } else {
                        response.setHeader("Content-Type", "text/html; charset=utf-8");
                    PrintWriter out = response.getWriter();
                    out.println(Script.href("/loginForm", "인증이 필요합니다."));
                    }
                   
                    return false;
                }

        return true;
    }

    
}
