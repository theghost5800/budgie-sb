package com.sap.broker.budgie.domain;

import java.util.Objects;

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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((error == null) ? 0 : error.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (null == object) {
            return false;
        }
        if (getClass() != object.getClass()) {
            return false;
        }
        ServiceBrokerError other = (ServiceBrokerError) object;
        if (!Objects.equals(description, other.description)) {
            return false;
        }
        if (!Objects.equals(error, other.error)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ServiceBrokerError [error=" + error + ", description=" + description + "]";
    }

}
