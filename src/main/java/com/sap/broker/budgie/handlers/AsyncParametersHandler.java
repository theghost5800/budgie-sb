package com.sap.broker.budgie.handlers;

import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Component;

import com.sap.broker.budgie.Constants;

@Component
public class AsyncParametersHandler {

    public boolean shouldExecuteOperationAsync(Map<String, Object> parameters) {
        return MapUtils.getBooleanValue(parameters, Constants.ASYNC_EXECUTION);
    }

    public int getTimeOfAsyncExecutionInSeconds(Map<String, Object> parameters) {
        return MapUtils.getIntValue(parameters, Constants.ASYNC_TIME_EXECUTION, Constants.DEFAULT_ASYNC_TIME_EXECUTION_IN_SECONDS);
    }
}
