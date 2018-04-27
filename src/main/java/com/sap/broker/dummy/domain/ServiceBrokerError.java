package com.sap.broker.dummy.domain;

public class ServiceBrokerError {

    private String error;
    private String description;

    public ServiceBrokerError(String error, String description) {
        this.error = error;
        this.description = description;
    }

    public String getError() {
        return error;
    }

    public String getDescription() {
        return description;
    }

    public void setError(String error) {
        this.error = error;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
