package com.sap.broker.budgie.helpers;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.UUID;

import com.sap.broker.budgie.domain.Plan;
import com.sap.broker.budgie.domain.Service;
import com.sap.broker.budgie.domain.Visitor;

public class GuidInsertingVisitor implements Visitor {

    @Override
    public void visit(Service service) {
        if (service.getId() == null) {
            service.setId(generateGuid(service.getName()));
        }
    }

    @Override
    public void visit(Service service, Plan plan) {
        if (plan.getId() == null) {
            plan.setId(generateGuid(service.getName(), plan.getName()));
        }
    }

    private UUID generateGuid(String... names) {
        return containsNullOrEmptyElements(names) ? null : UUID.nameUUIDFromBytes(getBytes(names));
    }

    private byte[] getBytes(String... names) {
        String concatenatedNames = String.join("", names);
        return concatenatedNames.getBytes(StandardCharsets.UTF_8);
    }

    private boolean containsNullOrEmptyElements(String... strings) {
        return Arrays.stream(strings)
            .anyMatch(string -> string == null || string.isEmpty());
    }

}
