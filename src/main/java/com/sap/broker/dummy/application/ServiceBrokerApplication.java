package com.sap.broker.dummy.application;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import com.sap.broker.dummy.providers.GsonMessageBodyReader;
import com.sap.broker.dummy.providers.GsonMessageBodyWriter;
import com.sap.broker.dummy.providers.ServiceBrokerExceptionMapper;
import com.sap.broker.dummy.resources.api.CatalogResource;
import com.sap.broker.dummy.resources.api.ServiceInstancesResource;

@ApplicationPath("/v2")
public class ServiceBrokerApplication extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        classes.add(CatalogResource.class);
        classes.add(ServiceInstancesResource.class);
        classes.add(GsonMessageBodyWriter.class);
        classes.add(GsonMessageBodyReader.class);
        classes.add(ServiceBrokerExceptionMapper.class);
        return classes;
    }

}
