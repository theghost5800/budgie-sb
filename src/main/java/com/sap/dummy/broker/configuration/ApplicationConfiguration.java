package com.sap.dummy.broker.configuration;

import java.text.MessageFormat;

import com.google.gson.Gson;
import com.sap.dummy.broker.domain.Catalog;

public class ApplicationConfiguration {

    private static final String CFG_CATALOG = "CATALOG";

    private Gson gson = new Gson();
    private Environment environment;
    private Catalog catalog;

    public ApplicationConfiguration() {
        this(new Environment());
    }
    
    public ApplicationConfiguration(Environment environment) {
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
