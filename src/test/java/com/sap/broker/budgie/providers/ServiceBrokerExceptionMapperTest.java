package com.sap.broker.budgie.providers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import javax.ws.rs.ForbiddenException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.sap.broker.budgie.Messages;
import com.sap.broker.budgie.domain.ServiceBrokerError;
import com.sap.broker.budgie.exception.ConflictException;
import com.sap.broker.budgie.exception.NotFoundException;

public class ServiceBrokerExceptionMapperTest {

    private ServiceBrokerExceptionMapper exceptionMapper = new ServiceBrokerExceptionMapper();

    @ParameterizedTest
    @MethodSource
    public void testToResponse(Throwable throwable, Response expectedResponse) {
        Response response = exceptionMapper.toResponse(throwable);

        assertEquals(expectedResponse.getStatus(), response.getStatus());
        assertEquals(expectedResponse.getMediaType(), response.getMediaType());
        assertEquals(expectedResponse.getEntity(), response.getEntity());
    }

    public static Stream<Arguments> testToResponse() {
// @formatter:off
        return Stream.of(
            Arguments.of(
                new ForbiddenException(),
                Response.status(Status.FORBIDDEN)
                        .entity(createServiceBrokerError("HTTP 403 Forbidden"))
                        .type(MediaType.APPLICATION_JSON)
                        .build()
            ),
            Arguments.of(
                new NotFoundException("Test message."),
                Response.status(Status.NOT_FOUND)
                        .entity(createServiceBrokerError("Test message."))
                        .type(MediaType.APPLICATION_JSON)
                        .build()
            ),
            Arguments.of(
                new ConflictException("Test message."),
                Response.status(Status.CONFLICT)
                        .entity(createServiceBrokerError("Test message."))
                        .type(MediaType.APPLICATION_JSON)
                        .build()
            ),
            Arguments.of(
                new IllegalArgumentException("Test message."),
                Response.status(Status.INTERNAL_SERVER_ERROR)
                        .entity(createServiceBrokerError("Test message."))
                        .type(MediaType.APPLICATION_JSON)
                        .build()
            ),
            Arguments.of(
                new NullPointerException(),
                Response.status(Status.INTERNAL_SERVER_ERROR)
                        .entity(createServiceBrokerError(Messages.AN_UNKNOWN_ERROR_OCCURRED))
                        .type(MediaType.APPLICATION_JSON)
                        .build()
            )
        );
// @formatter:off
    }

    private static ServiceBrokerError createServiceBrokerError(String description) {
        return new ServiceBrokerError(null, description);
    }
    
}
