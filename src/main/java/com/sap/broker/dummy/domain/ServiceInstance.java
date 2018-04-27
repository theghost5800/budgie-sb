package com.sap.broker.dummy.domain;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import com.google.gson.annotations.SerializedName;

public class ServiceInstance {

    private UUID id;
    @SerializedName("service_id")
    private UUID serviceId;
    @SerializedName("plan_id")
    private UUID planId;
    private Map<String, Object> parameters;

    public ServiceInstance(UUID id, UUID serviceId, UUID planId) {
        this.id = id;
        this.serviceId = serviceId;
        this.planId = planId;
    }

    public UUID getId() {
        return id;
    }

    public UUID getServiceId() {
        return serviceId;
    }

    public UUID getPlanId() {
        return planId;
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setServiceId(UUID serviceId) {
        this.serviceId = serviceId;
    }

    public void setPlanId(UUID planId) {
        this.planId = planId;
    }

    public void setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((serviceId == null) ? 0 : serviceId.hashCode());
        result = prime * result + ((planId == null) ? 0 : planId.hashCode());
        result = prime * result + ((parameters == null) ? 0 : parameters.hashCode());
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
        ServiceInstance other = (ServiceInstance) object;
        if (!Objects.equals(id, other.id)) {
            return false;
        }
        if (!Objects.equals(serviceId, other.serviceId)) {
            return false;
        }
        if (!Objects.equals(planId, other.planId)) {
            return false;
        }
        if (!Objects.equals(parameters, other.parameters)) {
            return false;
        }
        return true;
    }

}
