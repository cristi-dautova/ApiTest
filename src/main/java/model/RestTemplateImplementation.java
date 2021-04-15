package model;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

public class RestTemplateImplementation extends BaseRestClient {

    protected RestTemplate restTemplate = restTemplate();

    private RestTemplate restTemplate() {
        var factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(3000);
        factory.setReadTimeout(3000);
        return new RestTemplate(factory);
    }

    private String buildURI(String baseURL, Map<String, String> pathParameters, Map<String, String> queryParameters) {
        UriComponentsBuilder baseURI = UriComponentsBuilder.fromUriString(baseURL);
        if (pathParameters != null) {
            for (Map.Entry<String, String> entry : pathParameters.entrySet()) {
                baseURI.pathSegment(entry.getValue());
            }
        }
        if (queryParameters != null) {
            for (Map.Entry entry : queryParameters.entrySet()) {
                baseURI = baseURI.queryParam((String) entry.getKey(), entry.getValue());
            }
        }
        return baseURI.build().toString();
    }

    @Override
    public <T> Object buildRequest(model.HttpMethod method, String url, Map<String, String> pathParameters, Map<String, String> queryParameters, Map<String, String> headers, T value) {
        String uri = buildURI(url, pathParameters, queryParameters);
        HttpHeaders headersRestTemplate = new HttpHeaders();
        for (Map.Entry entry : headers.entrySet()) {
            headersRestTemplate.set((String) entry.getKey(), (String) entry.getValue());
        }
        HttpEntity<T> entity = new HttpEntity<>(value, headersRestTemplate);
        return new RequestParametersRestTemplate<T>(method, uri, entity);
    }

    @Override
    protected <T> ResponseParameters executeRequest(Object responseObj, Class<T> clazz) {
        model.HttpMethod httpMethod = ((RequestParametersRestTemplate) responseObj).method;
        String uri = ((RequestParametersRestTemplate) responseObj).uri;
        HttpEntity<T> entity = ((RequestParametersRestTemplate) responseObj).entity;
        ResponseEntity<String> response;
        switch (httpMethod) {
            case GET -> {
                response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
            }
            case DELETE -> {
                response = restTemplate.exchange(uri, HttpMethod.DELETE, entity, String.class);
            }
            case POST -> {
                response = restTemplate.exchange(uri, HttpMethod.POST, entity, String.class);
            }
            case PUT -> {
                response = restTemplate.exchange(uri, HttpMethod.PUT, entity, String.class);
            }
            default -> throw new IllegalStateException("Unexpected value: " + httpMethod);
        }
        return new ResponseParameters(response, clazz);
    }
}
