package com.sap.broker.budgie.domain;

public class ServiceOperation {

    private String description;
    private ServiceOperationState state;

    public ServiceOperation(String description, ServiceOperationState state) {
        this.description = description;
        this.state = state;
    }

    public String getDescription() {
        return description;
    }

    public ServiceOperationState getState() {
        return state;
    }

}
