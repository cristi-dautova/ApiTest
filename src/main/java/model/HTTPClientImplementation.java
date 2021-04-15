package model;

import lombok.SneakyThrows;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.util.Map;

import static utils.Serializer.serializeEntitiesToJson;

public class HTTPClientImplementation extends BaseRestClient {

    @SneakyThrows
    private String buildURIHttpClient(String baseURL, Map<String, String> pathParameters, Map<String, String> queryParameters) {
        StringBuilder basePath = new StringBuilder();
        for (Map.Entry<String, String> entry : pathParameters.entrySet()) {
            basePath.append("/").append(entry.getValue());
        }
        URIBuilder baseURI = new URIBuilder(baseURL);
        if (pathParameters != null) {
            baseURI.setPath(basePath.toString());
        }
        if (queryParameters != null) {
            for (Map.Entry<String, String> entry : queryParameters.entrySet()) {
                baseURI.setParameter(entry.getKey(), entry.getValue());
            }
        }
        return baseURI.build().toString();
    }

    @SneakyThrows
    @Override
    protected <T> Object buildRequest(HttpMethod method, String url, Map<String, String> pathParameters, Map<String, String> queryParameters, Map<String, String> headers, T value) {
        HttpUriRequest requestMethod = null;
        String uri = buildURIHttpClient(url, pathParameters, queryParameters);
        switch (method) {
            case GET -> requestMethod = new HttpGet(uri);
            case DELETE -> requestMethod = new HttpDelete(uri);
            case POST -> {
                requestMethod = new HttpPost(uri);
                String objectToJson = serializeEntitiesToJson(value);
                ((HttpPost) requestMethod).setEntity(new StringEntity(objectToJson));
            }
            case PUT -> {
                requestMethod = new HttpPut(uri);
                String objectToJson = serializeEntitiesToJson(value);
                ((HttpPut) requestMethod).setEntity(new StringEntity(objectToJson));
            }
        }
        for (Map.Entry entry : headers.entrySet()) {
            requestMethod.setHeader((String) entry.getKey(), (String) entry.getValue());
        }
        return requestMethod;
    }

    @SneakyThrows
    @Override
    protected <T> ResponseParameters executeRequest(Object requestObj, Class<T> clazz) {
        HttpUriRequest request = (HttpUriRequest) requestObj;
        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse closableResponse = httpClient.execute(request)) {
            return new ResponseParameters(closableResponse, clazz);
        }
    }
}
