package com.sap.broker.budgie.configuration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import com.sap.broker.budgie.Messages;
import com.sap.broker.budgie.TestUtil;
import com.sap.broker.budgie.domain.Catalog;
import com.sap.broker.budgie.domain.Visitor;

public class ApplicationConfigurationTest {

    private SpringConfiguration springConfiguration = new SpringConfiguration();

    @Spy
    private Environment environment = createEnvironment();
    @Spy
    private List<Visitor> catalogVisitors = createCatalogVisitors();
    @InjectMocks
    private ApplicationConfiguration configuration;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetCatalogWhenMissing() {
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> configuration.getCatalog());
        String expected = MessageFormat.format(Messages.ENVIRONMENT_VARIABLE_0_IS_NOT_SET, ApplicationConfiguration.CFG_CATALOG);
        assertEquals(expected, e.getMessage());
    }

    @Test
    public void testGetCatalogWhenInvalid() throws IOException {
        String catalogJson = TestUtil.loadResourceAsString("invalid-catalog.json", getClass());
        Mockito.when(environment.getVariable(ApplicationConfiguration.CFG_CATALOG))
            .thenReturn(catalogJson);
        assertThrows(IllegalArgumentException.class, () -> configuration.getCatalog());
    }

    @ParameterizedTest
    @MethodSource
    public void testGetCatalog(String catalogJson, String expectedCatalogJson) {
        Mockito.when(environment.getVariable(ApplicationConfiguration.CFG_CATALOG))
            .thenReturn(catalogJson);
        Catalog catalog = configuration.getCatalog();
        assertEquals(expectedCatalogJson, TestUtil.toFormattedJson(catalog));
    }

    public static Stream<Arguments> testGetCatalog() throws IOException {
// @formatter:off
        return Stream.of(
            Arguments.of(
                TestUtil.loadResourceAsString("catalog.json", ApplicationConfigurationTest.class),
                TestUtil.loadResourceAsString("catalog.json", ApplicationConfigurationTest.class)
            ),
            Arguments.of(
                TestUtil.loadResourceAsString("catalog-with-some-missing-guids.json", ApplicationConfigurationTest.class),
                TestUtil.loadResourceAsString("catalog.json", ApplicationConfigurationTest.class)
            ),
            Arguments.of(
                TestUtil.loadResourceAsString("catalog-with-missing-guids.json", ApplicationConfigurationTest.class),
                TestUtil.loadResourceAsString("catalog.json", ApplicationConfigurationTest.class)
            )
        );
// @formatter:on
    }

    private Environment createEnvironment() {
        return new Environment(springConfiguration.gson());
    }

    private List<Visitor> createCatalogVisitors() {
        List<Visitor> visitors = new ArrayList<>();
        visitors.addAll(springConfiguration.catalogVisitors());
        return visitors;
    }

}
