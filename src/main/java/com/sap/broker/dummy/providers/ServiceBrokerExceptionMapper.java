package com.sap.broker.dummy.providers;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.sap.broker.dummy.domain.ServiceBrokerError;
import com.sap.broker.dummy.exception.ConflictException;
import com.sap.broker.dummy.exception.NotFoundException;

@Provider
public class ServiceBrokerExceptionMapper implements ExceptionMapper<Throwable> {

    @Override
    public Response toResponse(Throwable throwable) {
        if (throwable instanceof WebApplicationException) {
            WebApplicationException e = (WebApplicationException) throwable;
            return e.getResponse();
        }

        if (throwable instanceof NotFoundException) {
            return Response.status(Status.NOT_FOUND)
                .entity(fromThrowable(throwable))
                .type(MediaType.APPLICATION_JSON)
                .build();
        }
        if (throwable instanceof ConflictException) {
            return Response.status(Status.CONFLICT)
                .entity(fromThrowable(throwable))
                .type(MediaType.APPLICATION_JSON)
                .build();
        }
        return Response.status(Status.INTERNAL_SERVER_ERROR)
            .entity(fromThrowable(throwable))
            .type(MediaType.APPLICATION_JSON)
            .build();
    }

    private ServiceBrokerError fromThrowable(Throwable throwable) {
        return new ServiceBrokerError(null, getErrorDescription(throwable));
    }

    private String getErrorDescription(Throwable throwable) {
        return throwable.getMessage() != null ? throwable.getMessage() : "An unknown error occurred!";
    }

}
