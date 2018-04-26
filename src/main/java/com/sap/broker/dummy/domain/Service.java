package com.sap.broker.dummy.domain;

import java.util.List;
import java.util.UUID;

import com.google.gson.annotations.SerializedName;

public class Service {

    private UUID id;
    private String name;
    private String description;
    private List<String> tags;
    private List<String> requires;
    private boolean bindable;
    @SerializedName("instances_retrievable")
    private boolean instancesRetrievable;
    @SerializedName("bindings_retrievable")
    private boolean bindingsRetrievable;
    @SerializedName("plan_updateable")
    private boolean planUpdateable;
    private List<Plan> plans;

    public Service(UUID id, String name, String description, boolean bindable, List<Plan> plans) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.bindable = bindable;
        this.plans = plans;
    }

    public Service withTags(List<String> tags) {
        this.tags = tags;
        return this;
    }

    public Service withRequires(List<String> requires) {
        this.requires = requires;
        return this;
    }

    public Service withInstancesRetrievable(boolean instancesRetrievable) {
        this.instancesRetrievable = instancesRetrievable;
        return this;
    }

    public Service withBindingsRetrievable(boolean bindingsRetrievable) {
        this.bindingsRetrievable = bindingsRetrievable;
        return this;
    }

    public Service withPlanUpdateable(boolean planUpdateable) {
        this.planUpdateable = planUpdateable;
        return this;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getTags() {
        return tags;
    }

    public List<String> getRequires() {
        return requires;
    }

    public boolean isBindable() {
        return bindable;
    }

    public boolean isInstancesRetrievable() {
        return instancesRetrievable;
    }

    public boolean isBindingsRetrievable() {
        return bindingsRetrievable;
    }

    public boolean isPlanUpdateable() {
        return planUpdateable;
    }

    public List<Plan> getPlans() {
        return plans;
    }

}
