package com.sap.broker.budgie.configuration.behavior;

import java.util.List;
import java.util.UUID;

public class BehaviorConfiguration {

    private UUID id;
    private Integer asyncDuration;
    private Integer syncDuration;
    private List<FailConfiguration> failConfigurations;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Integer getAsyncDuration() {
        return asyncDuration;
    }

    public void setAsyncDuration(Integer asyncDuration) {
        this.asyncDuration = asyncDuration;
    }

    public Integer getSyncDuration() {
        return syncDuration;
    }

    public void setSyncDuration(Integer syncDuration) {
        this.syncDuration = syncDuration;
    }

    public List<FailConfiguration> getFailConfigurations() {
        return failConfigurations;
    }

    public void setFailConfigurations(List<FailConfiguration> failConfigurations) {
        this.failConfigurations = failConfigurations;
    }
}
