package model;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static utils.UrlConstants.BASE_URL;

public class RestTemplateImplementation implements BaseRestClient {

    protected RestTemplate restTemplate = restTemplate();

    public RestTemplate restTemplate() {
        var factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(3000);
        factory.setReadTimeout(3000);
        return new RestTemplate(factory);
    }

    @Override
    public <T> ResponseParameters get(String endPont, String endPointParameter, Map<String, String> parameters, T value, Map<String, String> headers) {
        final String uri = BASE_URL + endPont + endPointParameter;
        HttpHeaders headersRestTemplate = new HttpHeaders();
        for (Map.Entry entry : headers.entrySet()){
            headersRestTemplate.set((String) entry.getKey(), (String) entry.getValue());
        }
        HttpEntity<T> entity = new HttpEntity<T>(value, headersRestTemplate);
        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class, parameters);

        return new ResponseParameters(response);
    }

    @Override
    public <T> ResponseParameters put(String endPoint, T value, Map<String, String> headers) {
        final String uri = BASE_URL + endPoint;
        HttpHeaders headersRestTemplate = new HttpHeaders();
        for (Map.Entry entry : headers.entrySet()){
            headersRestTemplate.set((String) entry.getKey(), (String) entry.getValue());
        }
        HttpEntity<T> entity = new HttpEntity<T>(value, headersRestTemplate);
        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.PUT, entity, String.class);
        return new ResponseParameters(response);
    }

    @Override
    public <T> ResponseParameters post(String endPoint, T value, Map<String, String> headers) {
        final String uri = BASE_URL + endPoint;
        HttpHeaders headersRestTemplate = new HttpHeaders();
        for (Map.Entry entry : headers.entrySet()){
            headersRestTemplate.set((String) entry.getKey(), (String) entry.getValue());
        }
        HttpEntity<T> entity = new HttpEntity<T>(value, headersRestTemplate);
        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, entity, String.class);
        return new ResponseParameters(response);
    }

    @Override
    public <T> ResponseParameters delete(String endPoint, String endPointParameter, Map<String, String> parameters, T value, Map<String, String> headers) {
        final String uri = BASE_URL + endPoint + endPointParameter;
        HttpHeaders headersRestTemplate = new HttpHeaders();
        for (Map.Entry entry : headers.entrySet()){
            headersRestTemplate.set((String) entry.getKey(), (String) entry.getValue());
        }
        HttpEntity<T> entity = new HttpEntity<T>(value, headersRestTemplate);
        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.DELETE, entity, String.class, parameters);
        return new ResponseParameters(response);
    }
}
