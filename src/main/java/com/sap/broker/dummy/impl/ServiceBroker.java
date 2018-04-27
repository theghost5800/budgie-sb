package com.sap.broker.dummy.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import com.sap.broker.dummy.configuration.ApplicationConfiguration;
import com.sap.broker.dummy.domain.Catalog;
import com.sap.broker.dummy.domain.ServiceInstance;

@Component
public class ServiceBroker {

    private ApplicationConfiguration configuration;
    private Map<UUID, ServiceInstance> serviceInstances = new HashMap<>();

    @Inject
    public ServiceBroker(ApplicationConfiguration configuration) {
        this.configuration = configuration;
    }

    public Catalog getCatalog() {
        return configuration.getCatalog();
    }

    public ServiceInstance get(UUID id) {
        return serviceInstances.get(id);
    }

    public void create(ServiceInstance serviceInstance) {
        serviceInstances.put(serviceInstance.getId(), serviceInstance);
    }

}
