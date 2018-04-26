package com.sap.dummy.broker.configuration;

import java.text.MessageFormat;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.sap.dummy.broker.domain.Catalog;

@Component
public class ApplicationConfiguration {

    private static final String CFG_CATALOG = "CATALOG";

    private Gson gson;
    private Environment environment;
    private Catalog catalog;

    @Inject
    public ApplicationConfiguration(Gson gson, Environment environment) {
        this.gson = gson;
        this.environment = environment;
    }

    public Catalog getCatalog() {
        if (catalog == null) {
            catalog = getCatalogFromEnvironment();
        }
        return catalog;
    }

    private Catalog getCatalogFromEnvironment() {
        String catalogJson = environment.getVariable(CFG_CATALOG);
        if (catalogJson == null) {
            throw new IllegalStateException(MessageFormat.format("{0} environment variable is not set.", CFG_CATALOG));
        }
        return gson.fromJson(catalogJson, Catalog.class);
    }

}
