package com.sap.broker.budgie.providers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;

import javax.ws.rs.core.MediaType;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class MediaTypeUtilTest {

    @ParameterizedTest
    @MethodSource
    public void testGetCharset(MediaType mediaType, Charset expectedCharset) {
        Charset charset = MediaTypeUtil.getCharset(mediaType);
        assertEquals(expectedCharset, charset);
    }

    public static Stream<Arguments> testGetCharset() {
// @formatter:off
        return Stream.of(
            Arguments.of(
                null,
                StandardCharsets.UTF_8
            ),
            Arguments.of(
                new MediaType(),
                StandardCharsets.UTF_8
            ),
            Arguments.of(
                new MediaType(MediaType.MEDIA_TYPE_WILDCARD, MediaType.MEDIA_TYPE_WILDCARD, StandardCharsets.UTF_16.name()),
                StandardCharsets.UTF_16
            )
        );
// @formatter:on
    }

}
