package com.sap.broker.budgie.configuration;

import javax.servlet.ServletContext;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

@Order(Ordered.HIGHEST_PRECEDENCE)
public class ApplicationInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(SpringConfiguration.class);
        servletContext.addListener(new ContextLoaderListener(context));
        disableDefaultContextLoaderListener(servletContext);
    }

    private void disableDefaultContextLoaderListener(ServletContext servletContext) {
        servletContext.setInitParameter("contextConfigLocation", "");
    }

}