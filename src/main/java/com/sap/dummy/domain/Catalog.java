package com.sap.dummy.domain;

import java.util.List;

public class Catalog {

    private List<Service> services;

    public Catalog(List<Service> services) {
        this.services = services;
    }

    public List<Service> getServices() {
        return services;
    }

}
