package jcore.config;

import jcore.common.MyInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	@Value("${cloud.gateway.uri}")
	String GATEWAY_URI;
	@Value("${spring.profiles.active}")
	String ACTIVE_PROFILE;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**")  // /** 만 사용
				.addResourceLocations("classpath:/static/");
	}

	@Override
	public void configurePathMatch(PathMatchConfigurer configurer) {
		configurer.setUseTrailingSlashMatch(true);
	}

	// 나머지 코드는 그대로 유지
	@Override
	public void addInterceptors(InterceptorRegistry reg) {
		reg.addInterceptor(new MyInterceptor(GATEWAY_URI, ACTIVE_PROFILE))
				.addPathPatterns("/*")
				.excludePathPatterns("/css/**", "/images/**", "/js/**");
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		if(ACTIVE_PROFILE.equals("local")) {
			registry.addMapping("/**")
					.allowedOrigins(GATEWAY_URI)
					.allowedOrigins("http://localhost:3000")
					.allowedMethods("*")
					.allowedHeaders("*");
		}
	}
}
