package model;

import io.restassured.http.Header;
import io.restassured.response.ResponseOptions;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.SneakyThrows;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static utils.Serializer.deserializeJson;

@Data
@AllArgsConstructor
public class ResponseParameters {

    private String body;
    private int statusCode;
    private Map<String, String> headers;
    private Object entity;

    @SneakyThrows
    public <T> ResponseParameters(ResponseOptions responseOptions, Class<T> clazz) {
        this(responseOptions.getBody().asString(), responseOptions.getStatusCode(), new HashMap<>(), null);
        for (Header header : responseOptions.getHeaders()) {
            headers.put(header.getName(), header.getValue());
        }
        this.entity = deserializeJson(getBody(), clazz);
    }

    @SneakyThrows
    public <T> ResponseParameters(ResponseEntity<String> responseEntity, Class<T> clazz) {
        this(responseEntity.getBody(), responseEntity.getStatusCodeValue(), new HashMap<>(), null);
        for (Map.Entry entry : responseEntity.getHeaders().entrySet()) {
            headers.put(entry.getKey().toString(), entry.getValue().toString());
        }
        this.entity = deserializeJson(getBody(), clazz);
    }

    public <T> ResponseParameters(CloseableHttpResponse closeableHttpResponse, Class<T> clazz) throws IOException {
        this(EntityUtils.toString(closeableHttpResponse.getEntity()), closeableHttpResponse.getStatusLine().getStatusCode(), new HashMap<>(), null);
        for (org.apache.http.Header header : closeableHttpResponse.getAllHeaders()) {
            headers.put(header.getName(), header.getValue());
        }
        this.entity = deserializeJson(getBody(), clazz);
    }
}
