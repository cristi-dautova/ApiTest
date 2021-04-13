package model;

import lombok.SneakyThrows;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static constants.UrlConstants.BASE_URL;

public class RestTemplateImplementation extends BaseRestClient {

    protected RestTemplate restTemplate = restTemplate();

    private RestTemplate restTemplate() {
        var factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(3000);
        factory.setReadTimeout(3000);
        return new RestTemplate(factory);
    }

    private <T> HttpEntity<T> buildRequest(T value, Map<String, String> headers) {
        HttpHeaders headersRestTemplate = new HttpHeaders();
        for (Map.Entry entry : headers.entrySet()) {
            headersRestTemplate.set((String) entry.getKey(), (String) entry.getValue());
        }
        return new HttpEntity<T>(value, headersRestTemplate);
    }

    @SneakyThrows
    @Override
    public <T> ResponseParameters executeRequest(model.HttpMethod method, String endPoint, Map<String, String> pathParameters, T value, Map<String, String> headers, Map<String, String> queryParameters) {
        ResponseEntity<String> response;
        HttpEntity<T> entity = buildRequest(value, headers);
        switch (method) {
            case GET -> {
                String endPointWithBraces = wrapEndPointPathParameterWithBraces(pathParameters);
                String uri = BASE_URL + endPoint + endPointWithBraces;
                response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class, pathParameters);
            }
            case DELETE -> {
                String endPointWithBraces = wrapEndPointPathParameterWithBraces(pathParameters);
                String uri = BASE_URL + endPoint + endPointWithBraces;
                response = restTemplate.exchange(uri, HttpMethod.DELETE, entity, String.class, pathParameters);
            }
            case POST -> {
                String uri = BASE_URL + endPoint;
                response = restTemplate.exchange(uri, HttpMethod.POST, entity, String.class);
            }
            case PUT -> {
                String uri = BASE_URL + endPoint;
                response = restTemplate.exchange(uri, HttpMethod.PUT, entity, String.class);
            }
            default -> throw new IllegalStateException("Unexpected value: " + method);
        }
        return new ResponseParameters(response);
    }
}
