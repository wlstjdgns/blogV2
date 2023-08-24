package shop.mtcoding.blogv2._core.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import shop.mtcoding.blogv2._core.filter.MyFilter1;

@Configuration
public class FilterConfig {
    
    @Bean
    public FilterRegistrationBean<?> myFilter1(){
    FilterRegistrationBean<?> bean = new FilterRegistrationBean<>(new MyFilter1()); //이 bean이 필터타입이 들어가있는 필터체인내부다.
    bean.addUrlPatterns("/*");
    bean.setOrder(0);
    return bean;
    }}
