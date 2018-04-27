package com.sap.broker.dummy.helpers;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

import com.sap.broker.dummy.domain.Plan;
import com.sap.broker.dummy.domain.Service;
import com.sap.broker.dummy.domain.Visitor;

public class GuidInsertingVisitor implements Visitor {

    @Override
    public void visit(Service service) {
        if (service.getId() == null) {
            service.setId(generateGuid(service.getName()));
        }
    }

    @Override
    public void visit(Plan plan) {
        if (plan.getId() == null) {
            plan.setId(generateGuid(plan.getName()));
        }
    }

    private UUID generateGuid(String name) {
        return UUID.nameUUIDFromBytes(name.getBytes(StandardCharsets.UTF_8));
    }

}
