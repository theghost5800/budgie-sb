package com.sap.broker.budgie.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.google.gson.annotations.SerializedName;

public class ServiceBinding {
    private UUID id;
    private Map<String, Object> context;
    @SerializedName("service_id")
    private UUID serviceId;
    @SerializedName("plan_id")
    private UUID planId;
    private Map<String, Object> parameters;
    @SerializedName("bind_resource")
    private BindResource bindResource;

    public ServiceBinding() {
        this.context = new HashMap<>();
        this.parameters = new HashMap<>();
    }

    public ServiceBinding(UUID id, Map<String, Object> context, UUID serviceId, UUID planId, Map<String, Object> parameters,
        BindResource bindResource) {
        this.id = id;
        this.context = context;
        this.serviceId = serviceId;
        this.planId = planId;
        this.parameters = parameters;
        this.bindResource = bindResource;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Map<String, Object> getContext() {
        return context;
    }

    public void setContext(Map<String, Object> context) {
        this.context = context;
    }

    public UUID getServiceId() {
        return serviceId;
    }

    public void setServiceId(UUID serviceId) {
        this.serviceId = serviceId;
    }

    public UUID getPlanId() {
        return planId;
    }

    public void setPlanId(UUID planId) {
        this.planId = planId;
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
    }

    public BindResource getBindResource() {
        return bindResource;
    }

    public void setBindResource(BindResource bindResource) {
        this.bindResource = bindResource;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((bindResource == null) ? 0 : bindResource.hashCode());
        result = prime * result + ((context == null) ? 0 : context.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((parameters == null) ? 0 : parameters.hashCode());
        result = prime * result + ((planId == null) ? 0 : planId.hashCode());
        result = prime * result + ((serviceId == null) ? 0 : serviceId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ServiceBinding other = (ServiceBinding) obj;
        if (bindResource == null) {
            if (other.bindResource != null)
                return false;
        } else if (!bindResource.equals(other.bindResource))
            return false;
        if (context == null) {
            if (other.context != null)
                return false;
        } else if (!context.equals(other.context))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (parameters == null) {
            if (other.parameters != null)
                return false;
        } else if (!parameters.equals(other.parameters))
            return false;
        if (planId == null) {
            if (other.planId != null)
                return false;
        } else if (!planId.equals(other.planId))
            return false;
        if (serviceId == null) {
            if (other.serviceId != null)
                return false;
        } else if (!serviceId.equals(other.serviceId))
            return false;
        return true;
    }

}
