package model;

import io.restassured.http.Header;
import io.restassured.response.ResponseOptions;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
public class ResponseParameters {

    private String body;
    private int statusCode;
    private Map<String, String> headers;

    public ResponseParameters(ResponseOptions responseOptions) {
        this(responseOptions.getBody().asString(), responseOptions.getStatusCode(), new HashMap<>());
        for(Header header : responseOptions.getHeaders()){
            headers.put(header.getName(), header.getValue());
        }
    }

    public ResponseParameters(ResponseEntity<String> responseEntity) {
        this(responseEntity.getBody(), responseEntity.getStatusCodeValue(), new HashMap<>());
        for(Map.Entry entry : responseEntity.getHeaders().entrySet()){
            headers.put(entry.getKey().toString(), entry.getValue().toString());
        }
    }

    public ResponseParameters(CloseableHttpResponse closeableHttpResponse) throws IOException {
        this(EntityUtils.toString(closeableHttpResponse.getEntity()), closeableHttpResponse.getStatusLine().getStatusCode(), new HashMap<>());
        for(org.apache.http.Header header : closeableHttpResponse.getAllHeaders()){
            headers.put(header.getName(), header.getValue());
        }

    }
}
