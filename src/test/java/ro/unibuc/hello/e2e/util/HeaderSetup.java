// package ro.unibuc.hello.e2e.util;

// import org.springframework.http.HttpHeaders;
// import org.springframework.http.client.ClientHttpRequest;
// import org.springframework.web.client.RequestCallback;

// import java.util.Map;

// public class HeaderSetup implements RequestCallback {

//     private final Map<String, String> requestHeaders;

//     public HeaderSetup(final Map<String, String> headers) {
//         this.requestHeaders = headers;
//     }

//     @Override
//     public void doWithRequest(ClientHttpRequest request) {
//         final HttpHeaders clientHeaders = request.getHeaders();
//         for (final Map.Entry<String, String> entry : requestHeaders.entrySet()) {
//             clientHeaders.add(entry.getKey(), entry.getValue());
//         }
//     }
// }
