package com.sap.broker.budgie.helpers;

public class AsyncOperationState {

    private State state;

    private String description;

    public AsyncOperationState() {
    }

    public AsyncOperationState(State state) {
        this.state = state;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public enum State {
        SUCCEEDED("succeeded"),
        FAILED("failed"),
        IN_PROGRESS("in progress");

        private final String name;

        State(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
