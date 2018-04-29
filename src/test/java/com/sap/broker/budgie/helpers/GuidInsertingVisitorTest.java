package com.sap.broker.budgie.helpers;

import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import com.sap.broker.budgie.domain.Catalog;
import com.sap.broker.budgie.domain.Service;

public class GuidInsertingVisitorTest {

    private final GuidInsertingVisitor visitor = new GuidInsertingVisitor();

    @Test
    public void testGuidsAreNotGeneratedForNullOrEmptyNames() {
        Service serviceA = new Service(null, null, null, true, Collections.emptyList());
        Service serviceB = new Service(null, "", null, true, Collections.emptyList());
        Catalog catalog = new Catalog(Arrays.asList(serviceA, serviceB));

        catalog.accept(visitor);

        assertNull(serviceA.getId());
        assertNull(serviceB.getId());
    }

}
