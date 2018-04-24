package com.sap.dummy.broker.configuration;

public class Environment {

    public String getVariable(String name) {
        return System.getenv(name);
    }

}
