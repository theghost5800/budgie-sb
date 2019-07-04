package com.sap.broker.budgie.impl;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import com.sap.broker.budgie.configuration.ApplicationConfiguration;
import com.sap.broker.budgie.domain.Catalog;
import com.sap.broker.budgie.domain.ServiceBinding;
import com.sap.broker.budgie.domain.ServiceInstance;
import com.sap.broker.budgie.domain.ServiceOperation;
import com.sap.broker.budgie.domain.ServiceOperationState;
import com.sap.broker.budgie.exception.NotFoundException;

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

    public Collection<ServiceInstance> getAll() {
        return serviceInstances.values();
    }

    public ServiceInstance get(UUID id) {
        return get(id, true);
    }

    public ServiceInstance get(UUID id, boolean required) {
        ServiceInstance serviceInstance = serviceInstances.get(id);
        if (serviceInstance == null && required) {
            throw new NotFoundException(MessageFormat.format("Service instance \"{0}\" not found!", id));
        }
        return serviceInstance;
    }

    public ServiceBinding getServiceBinding(UUID serviceInstanceId, UUID bindingId) {
        ServiceInstance serviceInstance = get(serviceInstanceId, true);
        return getServiceBinding(serviceInstance, bindingId, true);
    }

    public ServiceBinding getServiceBinding(ServiceInstance serviceInstance, UUID bindingId, boolean required) {
        ServiceBinding serviceBinding = serviceInstance.getServiceBinding(bindingId);
        if (serviceBinding == null && required) {
            throw new NotFoundException(
                MessageFormat.format("Binding \"{0}\" for service instance \"{1}\" not found!", serviceInstance.getId(), bindingId));
        }
        return serviceBinding;
    }

    public void create(ServiceInstance serviceInstance) {
        serviceInstance.setServiceOperation(new ServiceOperation("Successfully created", ServiceOperationState.SUCCEEDED));
        serviceInstances.put(serviceInstance.getId(), serviceInstance);
    }

    public void asyncCreate(ServiceInstance serviceInstance, int timeForCreation) {
        serviceInstance.setServiceOperation(new ServiceOperation(
            MessageFormat.format("Service instance will be ready after {0} seconds", timeForCreation), ServiceOperationState.IN_PROGRESS));
        new Thread(() -> {
            try {
                Thread.sleep(TimeUnit.SECONDS.toMillis(timeForCreation));
            } catch (InterruptedException e) {
                // Exit from sleep and set service operation to succeeded
            } finally {
                serviceInstance.setServiceOperation(new ServiceOperation("Successfull operation", ServiceOperationState.SUCCEEDED));
            }
        }).start();
        serviceInstances.put(serviceInstance.getId(), serviceInstance);
    }

    public void createServiceBinding(ServiceInstance serviceInstance, ServiceBinding serviceBinding) {
        serviceInstance.addServiceBinding(serviceBinding);
    }

    public void update(ServiceInstance serviceInstance) {
        serviceInstances.put(serviceInstance.getId(), serviceInstance);
    }

    public void deleteAll() {
        serviceInstances.clear();
    }

    public void delete(UUID id) {
        serviceInstances.remove(id);
    }

    public void asyncDelete(ServiceInstance serviceInstance, int timeForDeletion) {
        serviceInstance.setServiceOperation(new ServiceOperation(
            MessageFormat.format("Service instance will be ready after {0} seconds", timeForDeletion), ServiceOperationState.IN_PROGRESS));
        new Thread(() -> {
            try {
                Thread.sleep(TimeUnit.SECONDS.toMillis(timeForDeletion));
            } catch (InterruptedException e) {
                // Exit from sleep and set service operation to succeeded
            } finally {
                delete(serviceInstance.getId());
            }
        }).start();

    }

    public void deleteBinding(UUID serviceInstanceId, UUID bindingId, UUID serviceId, UUID planId) {
        ServiceInstance serviceInstance = get(serviceInstanceId, false);
        if (serviceInstance != null) {
            ServiceBinding serviceBinding = getServiceBinding(serviceInstance, bindingId, false);
            if (serviceBinding != null && serviceBinding.getServiceId()
                .equals(serviceId) && serviceBinding.getPlanId()
                    .equals(planId)) {
                serviceInstance.removeBinding(serviceBinding);
            }
        }
    }

}
