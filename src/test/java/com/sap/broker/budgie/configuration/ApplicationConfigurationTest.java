package com.sap.broker.budgie.configuration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.text.MessageFormat;
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

import com.google.gson.Gson;
import com.sap.broker.budgie.Messages;
import com.sap.broker.budgie.TestUtil;
import com.sap.broker.budgie.domain.Catalog;

public class ApplicationConfigurationTest {

    @Spy
    private Environment environment = createEnvironment();
    @InjectMocks
    private ApplicationConfiguration applicationConfiguration;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @ParameterizedTest
    @MethodSource
    public void testGetCatalog(String catalogJson, String expectedCatalogJson) {
        Mockito.when(environment.getVariable(ApplicationConfiguration.CFG_CATALOG))
            .thenReturn(catalogJson);
        Catalog catalog = applicationConfiguration.getCatalog();
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

    @Test
    public void testGetCatalogWhenMissing() {
        assertThrows(IllegalStateException.class, () -> applicationConfiguration.getCatalog(),
            MessageFormat.format(Messages.ENVIRONMENT_VARIABLE_IS_NOT_SET, ApplicationConfiguration.CFG_CATALOG));
    }

    private Environment createEnvironment() {
        return new Environment(new Gson());
    }

}
