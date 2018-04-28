package com.sap.broker.budgie.domain;

import java.util.List;

public class Catalog {

    private List<Service> services;

    public Catalog(List<Service> services) {
        this.services = services;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
        for (Service service : getServices()) {
            service.accept(visitor);
        }
    }

}
