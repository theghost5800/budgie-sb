package com.sap.broker.budgie.helpers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.sap.broker.budgie.Messages;
import com.sap.broker.budgie.TestUtil;
import com.sap.broker.budgie.domain.Catalog;

public class CatalogValidatingVisitorTest {

    private final CatalogValidatingVisitor validator = new CatalogValidatingVisitor();

    @Test
    public void testValidateWithNoServices() {
        Catalog catalog = new Catalog(Collections.emptyList());
        catalog.accept(validator);
    }

    @ParameterizedTest
    @MethodSource
    public void testValidate(Catalog catalog, String expectedExceptionMessage) {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> catalog.accept(validator));
        assertEquals(expectedExceptionMessage, e.getMessage());
    }

    public static Stream<Arguments> testValidate() throws IOException {
// @formatter:off
        return Stream.of(
            Arguments.of(
                TestUtil.loadResourceAsObject("invalid-catalog-missing-service-id-and-name.json", Catalog.class, CatalogValidatingVisitorTest.class),
                MessageFormat.format(Messages.FIELD_0_OF_SERVICE_1_IS_NULL_OR_EMPTY, "name", null)
            ),
            Arguments.of(
                TestUtil.loadResourceAsObject("invalid-catalog-missing-service-name-1.json", Catalog.class, CatalogValidatingVisitorTest.class),
                MessageFormat.format(Messages.FIELD_0_OF_SERVICE_1_IS_NULL_OR_EMPTY, "name", "be4a6cdd-20e5-3b17-9166-f1c182fef95a")
            ),
            Arguments.of(
                TestUtil.loadResourceAsObject("invalid-catalog-missing-service-name-2.json", Catalog.class, CatalogValidatingVisitorTest.class),
                MessageFormat.format(Messages.FIELD_0_OF_SERVICE_1_IS_NULL_OR_EMPTY, "name", "be4a6cdd-20e5-3b17-9166-f1c182fef95a")
            ),
            Arguments.of(
                TestUtil.loadResourceAsObject("invalid-catalog-missing-service-description-1.json", Catalog.class, CatalogValidatingVisitorTest.class),
                MessageFormat.format(Messages.FIELD_0_OF_SERVICE_1_IS_NULL_OR_EMPTY, "description", "postgresql-v9.4")
            ),
            Arguments.of(
                TestUtil.loadResourceAsObject("invalid-catalog-missing-service-description-2.json", Catalog.class, CatalogValidatingVisitorTest.class),
                MessageFormat.format(Messages.FIELD_0_OF_SERVICE_1_IS_NULL_OR_EMPTY, "description", "postgresql-v9.4")
            ),
            Arguments.of(
                TestUtil.loadResourceAsObject("invalid-catalog-missing-service-bindable.json", Catalog.class, CatalogValidatingVisitorTest.class),
                MessageFormat.format(Messages.FIELD_0_OF_SERVICE_1_IS_NULL, "bindable", "postgresql-v9.4")
            ),
            Arguments.of(
                TestUtil.loadResourceAsObject("invalid-catalog-missing-service-plans-1.json", Catalog.class, CatalogValidatingVisitorTest.class),
                MessageFormat.format(Messages.FIELD_0_OF_SERVICE_1_IS_NULL_OR_EMPTY, "plans", "postgresql-v9.4")
            ),
            Arguments.of(
                TestUtil.loadResourceAsObject("invalid-catalog-missing-service-plans-2.json", Catalog.class, CatalogValidatingVisitorTest.class),
                MessageFormat.format(Messages.FIELD_0_OF_SERVICE_1_IS_NULL_OR_EMPTY, "plans", "postgresql-v9.4")
            ),
            Arguments.of(
                TestUtil.loadResourceAsObject("invalid-catalog-missing-plan-name-1.json", Catalog.class, CatalogValidatingVisitorTest.class),
                MessageFormat.format(Messages.FIELD_0_OF_PLAN_1_IN_SERVICE_2_IS_NULL_OR_EMPTY, "name", "d6eef5a8-156d-31dc-9ab2-d3f20dde0a6d", "postgresql-v9.4")
            ),
            Arguments.of(
                TestUtil.loadResourceAsObject("invalid-catalog-missing-plan-name-2.json", Catalog.class, CatalogValidatingVisitorTest.class),
                MessageFormat.format(Messages.FIELD_0_OF_PLAN_1_IN_SERVICE_2_IS_NULL_OR_EMPTY, "name", "d6eef5a8-156d-31dc-9ab2-d3f20dde0a6d", "postgresql-v9.4")
            ),
            Arguments.of(
                TestUtil.loadResourceAsObject("invalid-catalog-missing-plan-description-1.json", Catalog.class, CatalogValidatingVisitorTest.class),
                MessageFormat.format(Messages.FIELD_0_OF_PLAN_1_IN_SERVICE_2_IS_NULL_OR_EMPTY, "description", "large", "postgresql-v9.4")
            ),
            Arguments.of(
                TestUtil.loadResourceAsObject("invalid-catalog-missing-plan-description-2.json", Catalog.class, CatalogValidatingVisitorTest.class),
                MessageFormat.format(Messages.FIELD_0_OF_PLAN_1_IN_SERVICE_2_IS_NULL_OR_EMPTY, "description", "large", "postgresql-v9.4")
            ),
            Arguments.of(
                TestUtil.loadResourceAsObject("invalid-catalog-missing-plan-id-and-name.json", Catalog.class, CatalogValidatingVisitorTest.class),
                MessageFormat.format(Messages.FIELD_0_OF_PLAN_1_IN_SERVICE_2_IS_NULL_OR_EMPTY, "name", null, "postgresql-v9.4")
            ),
            Arguments.of(
                TestUtil.loadResourceAsObject("invalid-catalog-null-services.json", Catalog.class, CatalogValidatingVisitorTest.class),
                MessageFormat.format(Messages.FIELD_0_IN_CATALOG_IS_NULL, "services")
            )
        );
// @formatter:on
    }

}
