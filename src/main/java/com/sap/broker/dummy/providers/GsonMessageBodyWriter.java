package com.sap.broker.dummy.providers;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import com.google.gson.Gson;

@Provider
@Produces(MediaType.APPLICATION_JSON)
public class GsonMessageBodyWriter<T> implements MessageBodyWriter<T> {

    @Override
    public long getSize(T t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return -1;
    }

    @Override
    public void writeTo(T t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType,
        MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws IOException, WebApplicationException {

        String stringJson = new Gson().toJson(t);
        byte[] binaryJson = stringJson.getBytes(getCharset(mediaType));
        entityStream.write(binaryJson);
    }

    private Charset getCharset(MediaType mediaType) {
        String charsetName = mediaType.getParameters()
            .get(MediaType.CHARSET_PARAMETER);
        return charsetName == null ? StandardCharsets.UTF_8 : Charset.forName(charsetName);
    }

    @Override
    public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return true;
    }

}
