// package ro.unibuc.hello.e2e.util;

// import org.springframework.http.client.ClientHttpResponse;

// import java.io.IOException;
// import java.io.InputStream;
// import java.io.StringWriter;

// import org.apache.commons.io.IOUtils;

// public class ResponseResults {

//     private final ClientHttpResponse theResponse;

//     private final String body;

//     public ResponseResults(final ClientHttpResponse response) throws IOException {
//         this.theResponse = response;
//         final InputStream bodyInputStream = response.getBody();
//         final StringWriter stringWriter = new StringWriter();
//         IOUtils.copy(bodyInputStream, stringWriter);
//         this.body = stringWriter.toString();
//     }

//     public ClientHttpResponse getTheResponse() {
//         return theResponse;
//     }

//     public String getBody() {
//         return body;
//     }

// }
