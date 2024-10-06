package com.lunastore.config;

import com.lunastore.interceptor.AuthInterceptor;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.ServletContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Value;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    @Value("${upload.dir:uploads/}")
    private String uploadDir;

    private final AuthInterceptor authInterceptor;
    private final ResourceLoader resourceLoader;

    private Path absoluteUploadPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // ResourceLoader를 사용하여 프로젝트 내 uploads 디렉토리의 절대 경로 설정
        absoluteUploadPath = Paths.get(System.getProperty("user.dir")).resolve(uploadDir).toAbsolutePath();

        File uploadDirectory = absoluteUploadPath.toFile();
        if (!uploadDirectory.exists()) {
            boolean created = uploadDirectory.mkdirs();
            if (created) {
                System.out.println("Upload directory created at: " + absoluteUploadPath.toString());
            } else {
                System.err.println("Failed to create upload directory at: " + absoluteUploadPath.toString());
            }
        }

        registry.addResourceHandler("/css/**")
                .addResourceLocations("classpath:/static/css/");
        registry.addResourceHandler("/js/**")
                .addResourceLocations("classpath:/static/js/");
        registry.addResourceHandler("/img/**")
                .addResourceLocations("classpath:/static/img/");
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:" + absoluteUploadPath.toString() + "/");
        registry.addResourceHandler("/font/**")
                .addResourceLocations("classpath:/static/font/");
        registry.addResourceHandler("/icon/**")
                .addResourceLocations("classpath:/static/icon/");
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:" + absoluteUploadPath.toString() + "/");
    }

    @PostConstruct
    public void init() {
        absoluteUploadPath = Paths.get(System.getProperty("user.dir")).resolve(uploadDir).toAbsolutePath();
        File uploadDirectory = absoluteUploadPath.toFile();
        if (!uploadDirectory.exists()) {
            boolean created = uploadDirectory.mkdirs();
            if (created) {
                System.out.println("Upload directory created at: " + absoluteUploadPath.toString());
            } else {
                System.err.println("Failed to create upload directory at: " + absoluteUploadPath.toString());
            }
        } else {
            System.out.println("Upload directory already exists at: " + absoluteUploadPath.toString());
        }
    }

    @Bean
    public LocaleResolver localeResolver() {
        AcceptHeaderLocaleResolver resolver = new AcceptHeaderLocaleResolver();
        resolver.setDefaultLocale(Locale.KOREAN); // 기본 언어 설정 (한국어)
        resolver.setSupportedLocales(Arrays.asList(Locale.KOREAN, Locale.JAPANESE)); // 지원하는 언어 목록
        return resolver;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
        interceptor.setParamName("lang");
        return interceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/buyer/**", "/seller/**", "/cart/**", "/item/**", "/tui-editor/**", "/file-upload/**", "/order/**") // 필요한 모든 패턴 추가
                .excludePathPatterns(
                        "/buyer/login",
                        "/buyer/buyerLoginProcess",
                        "/buyer/join",
                        "/buyer/buyerJoinProcess",
                        "/buyer/findPw",
                        "/buyer/findPwProcess",
                        "/buyer/verifyEmail",
                        "/buyer/buyerVerifyEmail",
                        "/seller/sellerLogin",
                        "/seller/sellerLoginProcess",
                        "/seller/sellerJoin",
                        "/seller/sellerJoinProcess",
                        "/seller/findPw",
                        "/seller/findPwProcess",
                        "/seller/sellerVerifyEmail",
                        "/seller/sellerVerifyEmailProcess",
                        "/api/**",
                        "/resources/**",
                        "/static/**",
                        "/",
                        "/seller/sellerLogin",
                        "/seller/businessnumCheckProcess",
                        "/seller/telCheckProcess",
                        "/seller/emailCheckProcess",
                        "/settlement/**"
                );
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages"); // messages.properties 위치
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
}