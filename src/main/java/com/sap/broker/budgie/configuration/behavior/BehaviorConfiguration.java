package com.sap.broker.budgie.configuration.behavior;

import java.util.List;

public class BehaviorConfiguration {

    private Integer async;
    private Integer sync;
    private List<FailConfiguration> failUpdate;
    private List<FailConfiguration> failCreate;
    private List<FailConfiguration> failDelete;
    private List<FailConfiguration> failBind;
    private List<FailConfiguration> failUnbind;

    public Integer getAsync() {
        return async;
    }

    public void setAsync(Integer async) {
        this.async = async;
    }

    public Integer getSync() {
        return sync;
    }

    public void setSync(Integer sync) {
        this.sync = sync;
    }

    public List<FailConfiguration> getFailUpdate() {
        return failUpdate;
    }

    public void setFailUpdate(List<FailConfiguration> failUpdate) {
        this.failUpdate = failUpdate;
    }

    public List<FailConfiguration> getFailCreate() {
        return failCreate;
    }

    public void setFailCreate(List<FailConfiguration> failCreate) {
        this.failCreate = failCreate;
    }

    public List<FailConfiguration> getFailDelete() {
        return failDelete;
    }

    public void setFailDelete(List<FailConfiguration> failDelete) {
        this.failDelete = failDelete;
    }

    public List<FailConfiguration> getFailBind() {
        return failBind;
    }

    public void setFailBind(List<FailConfiguration> failBind) {
        this.failBind = failBind;
    }

    public List<FailConfiguration> getFailUnbind() {
        return failUnbind;
    }

    public void setFailUnbind(List<FailConfiguration> failUnbind) {
        this.failUnbind = failUnbind;
    }
}
