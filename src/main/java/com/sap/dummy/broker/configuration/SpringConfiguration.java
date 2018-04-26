package com.sap.dummy.broker.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.google.gson.Gson;
import com.sap.dummy.broker.SpringComponentScanMarker;

@Configuration
@ComponentScan(basePackageClasses = SpringComponentScanMarker.class)
public class SpringConfiguration {

    @Bean
    public Gson gson() {
        return new Gson();
    }

}
