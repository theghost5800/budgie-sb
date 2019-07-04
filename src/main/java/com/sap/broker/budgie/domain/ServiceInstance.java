package com.sap.broker.budgie.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import com.google.gson.annotations.SerializedName;
import com.sap.broker.budgie.configuration.Exclude;

public class ServiceInstance {

    private UUID id;
    @SerializedName("service_id")
    private UUID serviceId;
    @SerializedName("plan_id")
    private UUID planId;
    private Map<String, Object> parameters;
    @Exclude
    private List<ServiceBinding> bindings;
    private ServiceOperation serviceOperation;

    public ServiceInstance() {
        this.parameters = new HashMap<>();
        this.bindings = new ArrayList<>();
    }

    public ServiceInstance(UUID id, UUID serviceId, UUID planId, List<ServiceBinding> bindings, ServiceOperation serviceOperation) {
        this.id = id;
        this.serviceId = serviceId;
        this.planId = planId;
        this.bindings = bindings;
        this.serviceOperation = serviceOperation;
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

    public List<ServiceBinding> getBindings() {
        return bindings;
    }

    public void setBindings(List<ServiceBinding> bindings) {
        this.bindings = bindings;
    }

    public ServiceOperation getServiceOperation() {
        return serviceOperation;
    }

    public void setServiceOperation(ServiceOperation serviceOperation) {
        this.serviceOperation = serviceOperation;
    }

    public void addServiceBinding(ServiceBinding binding) {
        bindings.add(binding);
    }

    public ServiceBinding getServiceBinding(UUID id) {
        for (ServiceBinding binding : bindings) {
            if (binding.getId()
                .equals(id)) {
                return binding;
            }
        }
        return null;
    }

    public void removeBinding(ServiceBinding serviceBinding) {
        bindings.remove(serviceBinding);
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
