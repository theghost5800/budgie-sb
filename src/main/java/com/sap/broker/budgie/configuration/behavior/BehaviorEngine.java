package com.sap.broker.budgie.configuration.behavior;

import com.sap.broker.budgie.configuration.ApplicationConfiguration;
import com.sap.broker.budgie.domain.Plan;
import com.sap.broker.budgie.domain.Service;
import com.sap.broker.budgie.domain.ServiceInstance;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.stream.Stream;

@Component
public class BehaviorEngine {

    private ApplicationConfiguration appConfiguration;
    private BehaviorConfiguration configuration;

    @Inject
    public BehaviorEngine(ApplicationConfiguration configuration) {
        this.appConfiguration = configuration;
    }

    public void setConfiguration(BehaviorConfiguration configuration) {
        this.configuration = configuration;
    }

    public BehaviorConfiguration getConfiguration() {
        return configuration;
    }

    public Integer getDuration() {
        if (isAsync()) {
            return configuration.getAsyncDuration();
        }
        if (configuration != null && configuration.getSyncDuration() != null) {
            return configuration.getSyncDuration();
        }
        return 0;
    }

    public boolean isAsync() {
        return configuration != null && configuration.getAsyncDuration() != null;
    }

    public Optional<Integer> shouldOperationFail(FailConfiguration.OperationType operationType, ServiceInstance serviceInstance) {
        if (configuration == null || configuration.getFailConfigurations() == null) {
            return Optional.empty();
        }
        return configuration.getFailConfigurations().stream()
            .filter(failConfig -> operationType.equals(failConfig.getOperationType()))
            .filter(failConfig -> all().or(byInstanceId()
            .and(byPlanId())
            .and(byServiceId())
            .and(byPlanName())
            .and(byPlanName()))
            .test(serviceInstance, failConfig))
            .map(FailConfiguration::getStatus).findFirst();
    }

    private BiPredicate<ServiceInstance, FailConfiguration> all() {
        return (serviceInstance, failConfiguration) -> failConfiguration.getAll() != null ? failConfiguration.getAll() : false;
    }

    private BiPredicate<ServiceInstance, FailConfiguration> byInstanceId() {
        return (serviceInstance, failConfig) -> {
            if (failConfig.getInstanceIds() == null) {
                return true;
            }
            return failConfig.getInstanceIds().stream()
                .anyMatch(id -> id.equals(serviceInstance.getId()));
        };
    }

    private BiPredicate<ServiceInstance, FailConfiguration> byPlanId() {
        return (serviceInstance, failConfig) -> {
            if (failConfig.getPlanIds() == null) {
                return true;
            }
            return failConfig.getPlanIds().stream()
                .anyMatch(id -> id.equals(serviceInstance.getPlanId()));
        };
    }

    private BiPredicate<ServiceInstance, FailConfiguration> byPlanName() {
        return (serviceInstance, failConfig) -> {
            if (failConfig.getPlanNames() == null) {
                return true;
            }
            return failConfig.getPlanNames()
                .stream()
                .map(this::getPlanByName)
                .filter(Optional::isPresent)
                .map(planOptional -> planOptional.get().getId())
                .anyMatch(id -> id.equals(serviceInstance.getPlanId()));
        };
    }

    private BiPredicate<ServiceInstance, FailConfiguration> byServiceId() {
        return (serviceInstance, failConfig) -> {
            if (failConfig.getServiceIds() == null) {
                return true;
            }
            return failConfig.getServiceIds()
                .stream()
                .anyMatch(id -> id.equals(serviceInstance.getServiceId()));
        };
    }

    private BiPredicate<ServiceInstance, FailConfiguration> byServiceName() {
        return (serviceInstance, failConfig) -> {
            if (failConfig.getServiceNames() == null) {
                return true;
            }
            return failConfig.getServiceNames()
                .stream()
                .map(this::getServiceByName)
                .filter(Optional::isPresent)
                .map(planOptional -> planOptional.get().getId())
                .anyMatch(id -> id.equals(serviceInstance.getServiceId()));
        };
    }

    private Optional<Service> getServiceByName(String name) {
        return getServices().filter(service -> service.getName().equals(name)).findFirst();
    }

    private Optional<Plan> getPlanByName(String name) {
        return getPlans().filter(plan -> plan.getName().equals(name)).findFirst();
    }

    private Stream<Service> getServices() {
        return appConfiguration.getCatalog().getServices().stream();
    }

    private Stream<Plan> getPlans() {
        return getServices().flatMap(service -> service.getPlans().stream());
    }
}
