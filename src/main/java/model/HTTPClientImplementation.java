package model;

import lombok.SneakyThrows;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.util.Map;

import static constants.UrlConstants.BASE_URL;
import static utils.Serializer.serializeEntitiesToJson;

public class HTTPClientImplementation extends BaseRestClient {

    public void buildRequest() {

    }

    @SneakyThrows
    @Override
    public <T> ResponseParameters executeRequest(HttpMethod method, String endPoint, Map<String, String> pathParameters, T value, Map<String, String> headers, Map<String, String> queryParameters) {
        HttpUriRequest requestMethod = null;
        switch (method) {
            case GET -> {
                String endPointValue = getEndPointValue(pathParameters);
                requestMethod = new HttpGet(BASE_URL + endPoint + endPointValue);
            }
            case DELETE -> {
                String endPointValue = getEndPointValue(pathParameters);
                requestMethod = new HttpDelete(BASE_URL + endPoint + endPointValue);
            }
            case POST -> {
                requestMethod = new HttpPost(BASE_URL + endPoint);
                String objectToJson = serializeEntitiesToJson(value);
                ((HttpPost) requestMethod).setEntity(new StringEntity(objectToJson));
            }
            case PUT -> {
                requestMethod = new HttpPut(BASE_URL + endPoint);
                String objectToJson = serializeEntitiesToJson(value);
                ((HttpPut) requestMethod).setEntity(new StringEntity(objectToJson));
            }
        }
        for (Map.Entry entry : headers.entrySet()) {
            requestMethod.setHeader((String) entry.getKey(), (String) entry.getValue());
        }
        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(requestMethod)) {
            return new ResponseParameters(response);
        }
    }

    private String getEndPointValue(Map<String, String> pathParameters) {
        StringBuilder endPointValue = new StringBuilder();
        if (pathParameters != null) {
            for (Map.Entry entry : pathParameters.entrySet()) {
                endPointValue.append("/").append((String) entry.getValue());
            }
        }
        return endPointValue.toString();
    }
}
