package ro.unibuc.hello.e2e.util;

import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

public class ResponseErrorHandler implements org.springframework.web.client.ResponseErrorHandler {

    private ResponseResults results = null;
    private Boolean hadError = false;

    public ResponseResults getResults() {
        return results;
    }

    public Boolean getHadError() {
        return hadError;
    }

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        hadError = response.getRawStatusCode() >= 400;
        return hadError;
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        results = new ResponseResults(response);
    }
}
