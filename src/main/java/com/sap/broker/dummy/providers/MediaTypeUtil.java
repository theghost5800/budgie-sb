package com.sap.broker.dummy.providers;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import javax.ws.rs.core.MediaType;

public class MediaTypeUtil {

    public static Charset getCharset(MediaType mediaType) {
        String charsetName = mediaType.getParameters()
            .get(MediaType.CHARSET_PARAMETER);
        return charsetName == null ? StandardCharsets.UTF_8 : Charset.forName(charsetName);
    }

}
