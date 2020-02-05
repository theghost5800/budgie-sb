package com.sap.broker.budgie.helpers;

import com.sap.broker.budgie.configuration.behavior.BehaviorConfiguration;

public class BehaviorConfigurationValidator {

    public boolean validate(BehaviorConfiguration configuration) {
        return validateAsyncDuration(configuration) &&
                validateSyncDuration(configuration) &&
                validateOperationType(configuration) &&
                validateStatus(configuration);
    }

    private boolean validateAsyncDuration(BehaviorConfiguration configuration) {
        return configuration.getAsyncDuration() == null || configuration.getAsyncDuration() >= 0;
    }

    private boolean validateSyncDuration(BehaviorConfiguration configuration) {
        return configuration.getSyncDuration() == null || configuration.getSyncDuration() >= 0;
    }

    private boolean validateOperationType(BehaviorConfiguration configuration) {
        if (configuration.getFailConfigurations() != null) {
            return configuration.getFailConfigurations().stream().allMatch(config -> config.getOperationType() != null);
        }
        return true;
    }

    private boolean validateStatus(BehaviorConfiguration configuration) {
        if (configuration.getFailConfigurations() != null) {
            return configuration.getFailConfigurations().stream().allMatch(config -> {
                Integer status = config.getStatus();
                return status != null && status >= 400 && status < 600;
            });
        }
        return true;
    }
}
