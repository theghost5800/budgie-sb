package com.sap.broker.budgie.domain;

import java.util.UUID;

public class Plan {

    private UUID id;
    private String name;
    private String description;
    private Boolean free;
    private Boolean bindable;

    public Plan(UUID id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
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

    public Boolean isFree() {
        return free;
    }

    public Boolean isBindable() {
        return bindable;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setFree(Boolean free) {
        this.free = free;
    }

    public void setBindable(Boolean bindable) {
        this.bindable = bindable;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

}
