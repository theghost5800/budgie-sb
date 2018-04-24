package com.sap.dummy.configuration;

public class Environment {

    public String getVariable(String name) {
        return System.getenv(name);
    }

}
