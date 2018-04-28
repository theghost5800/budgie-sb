package com.sap.broker.budgie.configuration;

import org.springframework.stereotype.Component;

@Component
public class Environment {

    public String getVariable(String name) {
        return System.getenv(name);
    }

}
