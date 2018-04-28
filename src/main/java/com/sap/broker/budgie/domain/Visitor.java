package com.sap.broker.budgie.domain;

public interface Visitor {

    default void visit(Catalog catalog) {
    }

    default void visit(Service service) {
    }

    default void visit(Plan plan) {
    }

}
