package com.sap.dummy.broker.domain;

import java.util.UUID;

public class Plan {

    private UUID id;
    private String name;
    private String description;
    private boolean free;
    private boolean bindable;

    public Plan(UUID id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public void withFree(boolean free) {
        this.free = free;
    }

    public void withBindable(boolean bindable) {
        this.bindable = bindable;
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

    public boolean isFree() {
        return free;
    }

    public boolean isBindable() {
        return bindable;
    }

}
