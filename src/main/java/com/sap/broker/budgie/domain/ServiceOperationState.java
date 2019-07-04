package com.sap.broker.budgie.domain;

import java.text.MessageFormat;

import com.google.gson.annotations.SerializedName;

public enum ServiceOperationState {

    @SerializedName("succeeded")
    SUCCEEDED("succeeded"), @SerializedName("failed")
    FAILED("failed"), @SerializedName("in progress")
    IN_PROGRESS("in progress");

    private final String name;

    private ServiceOperationState(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public static ServiceOperationState fromString(String value) {
        for (ServiceOperationState state : ServiceOperationState.values()) {
            if (state.toString()
                .equals(value)) {
                return state;
            }
        }
        throw new IllegalArgumentException(MessageFormat.format("Illegal service operation state: {0}", value));
    }

}
