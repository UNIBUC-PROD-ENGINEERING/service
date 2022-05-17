package ro.unibuc.slots.e2e.util;

import org.apache.commons.io.IOUtils;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.io.StringWriter;

public class ResponseResults {
    private final ClientHttpResponse theResponse;

    private final String body;

    public ResponseResults(final ClientHttpResponse response) throws IOException {
        this.theResponse = response;

        final StringWriter stringWriter = new StringWriter();
        IOUtils.copy(response.getBody(), stringWriter);
        this.body = stringWriter.toString();
    }

    public ClientHttpResponse getTheResponse() {
        return theResponse;
    }

    public String getBody() {
        return body;
    }
}
