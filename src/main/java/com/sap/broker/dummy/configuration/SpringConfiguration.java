package com.sap.broker.dummy.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.google.gson.Gson;
import com.sap.broker.dummy.SpringComponentScanMarker;

@Configuration
@ComponentScan(basePackageClasses = SpringComponentScanMarker.class)
public class SpringConfiguration {

    @Bean
    public Gson gson() {
        return new Gson();
    }

}
