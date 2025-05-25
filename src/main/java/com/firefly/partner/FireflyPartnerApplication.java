package com.firefly.partner;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

public class FireflyPartnerApplication implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) {

        // 创建Spring应用上下文
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(com.firefly.partner.config.AppConfig.class);
        context.register(com.firefly.partner.config.WebConfig.class);
        context.register(com.firefly.partner.config.BeanConfig.class);

        // 创建和注册DispatcherServlet
        DispatcherServlet servlet = new DispatcherServlet(context);
        ServletRegistration.Dynamic registration = servletContext.addServlet("app", servlet);
        registration.setLoadOnStartup(1);
        registration.addMapping("/");
    }


}