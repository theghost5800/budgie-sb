package com.sap.broker.budgie.configuration.behavior;

import com.sap.broker.budgie.configuration.ApplicationConfiguration;
import com.sap.broker.budgie.domain.Catalog;
import com.sap.broker.budgie.domain.Plan;
import com.sap.broker.budgie.domain.Service;
import com.sap.broker.budgie.domain.ServiceInstance;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BehaviorEngineTest {

    private static final Plan PLAN = new Plan(UUID.randomUUID(), "plan1", "plan description");
    private static final Service SERVICE = new Service(UUID.randomUUID(), "service1", "service description", true, Arrays.asList(PLAN));

    private static final ServiceInstance INSTANCE = new ServiceInstance(UUID.randomUUID(), SERVICE.getId(), PLAN.getId());
    private static final Catalog CATALOG = new Catalog(Arrays.asList(SERVICE));

    private static final ApplicationConfiguration APP_CONFIG = mock(ApplicationConfiguration.class);
    private BehaviorEngine behaviorEngine;

    @BeforeAll public static void init() {
        when(APP_CONFIG.getCatalog()).thenReturn(CATALOG);
    }

    @BeforeEach public void setUp() {
        behaviorEngine = new BehaviorEngine(APP_CONFIG);
    }

    @Test public void testShouldCreateFailWithPlanName() {
        BehaviorConfiguration behaviorConfig = new BehaviorConfiguration();
        behaviorConfig.setFailCreate(Arrays.asList(getFailConfigForPlanName(400)));
        behaviorEngine.setConfiguration(behaviorConfig);
        Optional<Integer> optionalStatus = behaviorEngine.shouldCreateFail(INSTANCE);
        assertTrue(optionalStatus.isPresent());
        assertEquals(new Integer(400), optionalStatus.get());
    }

    @Test public void testShouldUpdateFailWithPlanId() {
        BehaviorConfiguration behaviorConfig = new BehaviorConfiguration();
        behaviorConfig.setFailUpdate(Arrays.asList(getFailConfigForPlanId(404)));
        behaviorEngine.setConfiguration(behaviorConfig);
        Optional<Integer> optionalStatus = behaviorEngine.shouldUpdateFail(INSTANCE);
        assertTrue(optionalStatus.isPresent());
        assertEquals(new Integer(404), optionalStatus.get());
    }

    @Test public void testShouldDeleteFailWithServiceId() {
        BehaviorConfiguration behaviorConfig = new BehaviorConfiguration();
        behaviorConfig.setFailDelete(Arrays.asList(getFailConfigForServiceId(410)));
        behaviorEngine.setConfiguration(behaviorConfig);
        Optional<Integer> optionalStatus = behaviorEngine.shouldDeleteFail(INSTANCE);
        assertTrue(optionalStatus.isPresent());
        assertEquals(new Integer(410), optionalStatus.get());
    }

    @Test public void testShouldBindFailWithServiceName() {
        BehaviorConfiguration behaviorConfig = new BehaviorConfiguration();
        behaviorConfig.setFailBind(Arrays.asList(getFailConfigForServiceName(400)));
        behaviorEngine.setConfiguration(behaviorConfig);
        Optional<Integer> optionalStatus = behaviorEngine.shouldBindFail(INSTANCE);
        assertTrue(optionalStatus.isPresent());
        assertEquals(new Integer(400), optionalStatus.get());
    }

    @Test public void testShouldUnbindFailWithInstanceId() {
        BehaviorConfiguration behaviorConfig = new BehaviorConfiguration();
        behaviorConfig.setFailUnbind(Arrays.asList(getFailConfigForInstanceId(404)));
        behaviorEngine.setConfiguration(behaviorConfig);
        Optional<Integer> optionalStatus = behaviorEngine.shouldUnbindFail(INSTANCE);
        assertTrue(optionalStatus.isPresent());
        assertEquals(new Integer(404), optionalStatus.get());
    }

    @Test public void testShouldCreateFailAll() {
        BehaviorConfiguration behaviorConfig = new BehaviorConfiguration();
        FailConfiguration failConfiguration = new FailConfiguration();
        failConfiguration.setAll(true);
        failConfiguration.setStatus(400);
        behaviorConfig.setFailCreate(Arrays.asList(failConfiguration));
        behaviorEngine.setConfiguration(behaviorConfig);
        Optional<Integer> optionalStatus = behaviorEngine.shouldCreateFail(INSTANCE);
        assertTrue(optionalStatus.isPresent());
        assertEquals(new Integer(400), optionalStatus.get());
    }

    @Test public void testShouldCreateFailWhenNoBehaviorConfiguration() {
        Optional<Integer> optionalStatus = behaviorEngine.shouldCreateFail(INSTANCE);
        assertFalse(optionalStatus.isPresent());
    }

    @Test public void testShouldUpdateFailWhenNoBehaviorConfiguration() {
        Optional<Integer> optionalStatus = behaviorEngine.shouldUpdateFail(INSTANCE);
        assertFalse(optionalStatus.isPresent());
    }

    @Test public void testShouldDeleteFailWhenNoBehaviorConfiguration() {
        Optional<Integer> optionalStatus = behaviorEngine.shouldDeleteFail(INSTANCE);
        assertFalse(optionalStatus.isPresent());
    }

    @Test public void testShouldBindFailWhenNoBehaviorConfiguration() {
        Optional<Integer> optionalStatus = behaviorEngine.shouldBindFail(INSTANCE);
        assertFalse(optionalStatus.isPresent());
    }

    @Test public void testShouldUnbindFailWhenNoBehaviorConfiguration() {
        Optional<Integer> optionalStatus = behaviorEngine.shouldUnbindFail(INSTANCE);
        assertFalse(optionalStatus.isPresent());
    }

    @Test public void testGetTimeoutWhenAsync() {
        BehaviorConfiguration behaviorConfig = new BehaviorConfiguration();
        behaviorConfig.setAsync(200);
        behaviorEngine.setConfiguration(behaviorConfig);
        assertEquals(new Integer(200), behaviorEngine.getTimeout());
        assertTrue(behaviorEngine.isAsync());
    }

    @Test public void testGetTimeoutWhenSync() {
        BehaviorConfiguration behaviorConfig = new BehaviorConfiguration();
        behaviorConfig.setSync(300);
        behaviorEngine.setConfiguration(behaviorConfig);
        assertEquals(new Integer(300), behaviorEngine.getTimeout());
        assertFalse(behaviorEngine.isAsync());
    }

    @Test public void testGetTimeoutWhenNoBehaviorConfiguration() {
        assertEquals(new Integer(0), behaviorEngine.getTimeout());
    }

    @Test public void testBeSynchronousWhenNoBehaviorConfiguration() {
        assertFalse(behaviorEngine.isAsync());
    }

    private FailConfiguration getFailConfigForPlanName(int status) {
        FailConfiguration failConfig = new FailConfiguration();
        failConfig.setPlanNames(Arrays.asList(PLAN.getName()));
        failConfig.setStatus(status);
        return failConfig;
    }

    private FailConfiguration getFailConfigForPlanId(int status) {
        FailConfiguration failConfig = new FailConfiguration();
        failConfig.setPlanIds(Arrays.asList(PLAN.getId()));
        failConfig.setStatus(status);
        return failConfig;
    }

    private FailConfiguration getFailConfigForServiceName(int status) {
        FailConfiguration failConfig = new FailConfiguration();
        failConfig.setServiceNames(Arrays.asList(SERVICE.getName()));
        failConfig.setStatus(status);
        return failConfig;
    }

    private FailConfiguration getFailConfigForServiceId(int status) {
        FailConfiguration failConfig = new FailConfiguration();
        failConfig.setServiceIds(Arrays.asList(SERVICE.getId()));
        failConfig.setStatus(status);
        return failConfig;
    }

    private FailConfiguration getFailConfigForInstanceId(int status) {
        FailConfiguration failConfig = new FailConfiguration();
        failConfig.setInstanceIds(Arrays.asList(INSTANCE.getId()));
        failConfig.setStatus(status);
        return failConfig;
    }

}
