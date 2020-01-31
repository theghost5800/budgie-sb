package com.sap.broker.budgie.configuration.behavior;

import java.util.List;
import java.util.UUID;

public class FailConfiguration {

    private Boolean all;
    private Integer status;
    private List<UUID> serviceIds;
    private List<UUID> planIds;
    private List<UUID> instanceIds;
    private List<String> serviceNames;
    private List<String> planNames;

    public Boolean getAll() {
        return all;
    }

    public void setAll(Boolean all) {
        this.all = all;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<UUID> getServiceIds() {
        return serviceIds;
    }

    public void setServiceIds(List<UUID> serviceIds) {
        this.serviceIds = serviceIds;
    }

    public List<UUID> getPlanIds() {
        return planIds;
    }

    public void setPlanIds(List<UUID> planIds) {
        this.planIds = planIds;
    }

    public List<UUID> getInstanceIds() {
        return instanceIds;
    }

    public void setInstanceIds(List<UUID> instanceIds) {
        this.instanceIds = instanceIds;
    }

    public List<String> getServiceNames() {
        return serviceNames;
    }

    public void setServiceNames(List<String> serviceNames) {
        this.serviceNames = serviceNames;
    }

    public List<String> getPlanNames() {
        return planNames;
    }

    public void setPlanNames(List<String> planNames) {
        this.planNames = planNames;
    }
}
