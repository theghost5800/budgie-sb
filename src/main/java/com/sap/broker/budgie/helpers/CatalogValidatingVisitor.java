package com.sap.broker.budgie.helpers;

import java.text.MessageFormat;

import org.springframework.util.Assert;

import com.sap.broker.budgie.Messages;
import com.sap.broker.budgie.domain.Catalog;
import com.sap.broker.budgie.domain.Plan;
import com.sap.broker.budgie.domain.Service;
import com.sap.broker.budgie.domain.Visitor;

public class CatalogValidatingVisitor implements Visitor {

    @Override
    public void visit(Catalog catalog) {
        validateCatalogServices(catalog);
    }

    @Override
    public void visit(Service service) {
        validateServiceName(service);
        validateServiceDescription(service);
        validateServiceBindable(service);
        validateServicePlans(service);
    }

    @Override
    public void visit(Service service, Plan plan) {
        validatePlanName(service, plan);
        validatePlanDescription(service, plan);
    }

    private void validateCatalogServices(Catalog catalog) {
        Assert.notNull(catalog.getServices(), MessageFormat.format(Messages.FIELD_0_IN_CATALOG_IS_NULL, "services"));
    }

    private void validateServiceName(Service service) {
        Assert.hasText(service.getName(), MessageFormat.format(Messages.FIELD_0_OF_SERVICE_1_IS_NULL_OR_EMPTY, "name", service.getId()));
    }

    private void validateServiceDescription(Service service) {
        Assert.hasText(service.getDescription(),
            MessageFormat.format(Messages.FIELD_0_OF_SERVICE_1_IS_NULL_OR_EMPTY, "description", service.getName()));
    }

    private void validateServiceBindable(Service service) {
        Assert.notNull(service.isBindable(), MessageFormat.format(Messages.FIELD_0_OF_SERVICE_1_IS_NULL, "bindable", service.getName()));
    }

    private void validateServicePlans(Service service) {
        Assert.notEmpty(service.getPlans(),
            MessageFormat.format(Messages.FIELD_0_OF_SERVICE_1_IS_NULL_OR_EMPTY, "plans", service.getName()));
    }

    private void validatePlanName(Service service, Plan plan) {
        Assert.hasText(plan.getName(),
            MessageFormat.format(Messages.FIELD_0_OF_PLAN_1_IN_SERVICE_2_IS_NULL_OR_EMPTY, "name", plan.getId(), service.getName()));
    }

    private void validatePlanDescription(Service service, Plan plan) {
        Assert.hasText(plan.getDescription(), MessageFormat.format(Messages.FIELD_0_OF_PLAN_1_IN_SERVICE_2_IS_NULL_OR_EMPTY,
            "description", plan.getName(), service.getName()));
    }

}
