package com.sap.broker.budgie.configuration;

import java.text.MessageFormat;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import com.sap.broker.budgie.Messages;
import com.sap.broker.budgie.domain.Catalog;
import com.sap.broker.budgie.helpers.GuidInsertingVisitor;

@Component
public class ApplicationConfiguration {

    static final String CFG_CATALOG = "CATALOG";

    private Environment environment;
    private Catalog catalog;

    @Inject
    public ApplicationConfiguration(Environment environment) {
        this.environment = environment;
    }

    public Catalog getCatalog() {
        if (catalog == null) {
            catalog = getCatalogFromEnvironment();
            fillInMissingGuids(catalog);
        }
        return catalog;
    }

    private Catalog getCatalogFromEnvironment() {
        Catalog catalog = environment.getJsonVariable(CFG_CATALOG, Catalog.class);
        if (catalog == null) {
            throw new IllegalStateException(MessageFormat.format(Messages.ENVIRONMENT_VARIABLE_IS_NOT_SET, CFG_CATALOG));
        }
        return catalog;
    }

    private void fillInMissingGuids(Catalog catalog) {
        catalog.accept(new GuidInsertingVisitor());
    }

}
