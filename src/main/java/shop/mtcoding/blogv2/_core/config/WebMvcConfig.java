package shop.mtcoding.blogv2._core.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

import shop.mtcoding.blogv2._core.intercepter.LoginTnterceptor;

//사진 외부경로 설정하고 싶을때//문지기가 보는 문서를 체인지하는거야. 기존걸 놔두고 추가하는거야(강사님튜브참고)
@Configuration //설정파일은 해당 어노테이션 붙여줘야해......
public class WebMvcConfig implements WebMvcConfigurer{
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        WebMvcConfigurer.super.addResourceHandlers(registry);

        registry.addResourceHandler("/images/**")
            .addResourceLocations("file:"+"./images/")
            .setCachePeriod(10) // 10 (초)
            .resourceChain(true)
            .addResolver(new PathResourceResolver());



    }
@Override
public void addInterceptors(InterceptorRegistry registry){
    registry.addInterceptor(new LoginTnterceptor())
    .addPathPatterns("/api/**")
    .addPathPatterns("/user/update", "/user/updateForm")
    .addPathPatterns("/board/**") // 발동 조건
    .excludePathPatterns("/board/{id:[0-9]+}"); // 발동 제외
    

}



}
