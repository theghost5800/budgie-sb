package com.sap.dummy.resources.api;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.UUID;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import com.sap.dummy.domain.Catalog;
import com.sap.dummy.domain.Plan;
import com.sap.dummy.domain.Service;

@Path("/catalog")
public class CatalogResource {

    @GET
    public Catalog getCatalog() {
        return generateStaticCatalog();
    }

    private Catalog generateStaticCatalog() {
        Plan planFooA = new Plan(uuidFromName("foo-a"), "foo-a", "foo-a");
        Plan planFooB = new Plan(uuidFromName("foo-b"), "foo-b", "foo-b");
        planFooB.withFree(false);
        Service serviceFoo = new Service(uuidFromName("foo"), "foo", "foo", false, Arrays.asList(planFooA, planFooB));
        Plan planBarA = new Plan(uuidFromName("bar-a"), "bar-a", "bar-a");
        Plan planBarB = new Plan(uuidFromName("bar-b"), "bar-b", "bar-b");
        planFooB.withFree(false);
        Service serviceBar = new Service(uuidFromName("bar"), "bar", "bar", false, Arrays.asList(planBarA, planBarB));
        return new Catalog(Arrays.asList(serviceFoo, serviceBar));
    }

    private UUID uuidFromName(String name) {
        return UUID.nameUUIDFromBytes(name.getBytes(StandardCharsets.UTF_8));
    }

}
