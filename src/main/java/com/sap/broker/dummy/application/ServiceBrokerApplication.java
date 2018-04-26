package com.sap.broker.dummy.application;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import com.sap.broker.dummy.providers.GsonMessageBodyWriter;
import com.sap.broker.dummy.resources.api.CatalogResource;

@ApplicationPath("/v2")
public class ServiceBrokerApplication extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<Class<?>>();
        classes.add(CatalogResource.class);
        classes.add(GsonMessageBodyWriter.class);
        return classes;
    }

}