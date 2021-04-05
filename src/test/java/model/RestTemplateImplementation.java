package model;

import org.springframework.http.HttpEntity;
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
    public ResponseParameters get(String endPont, String endPointParameter, Map<String, String> parameters) {
        final String uri = BASE_URL + endPont + endPointParameter;
        int statusCode = restTemplate.getForEntity(uri, String.class, parameters).getStatusCodeValue();
        return new ResponseParameters(restTemplate.getForObject(uri, String.class, parameters), statusCode, null);
    }

    @Override
    public <T> ResponseParameters put(String endPoint, T value) {
        final String uri = BASE_URL + endPoint;
        //restTemplate.put(uri, value);
        HttpEntity<T> entity = new HttpEntity<T>(value);
        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.PUT, entity, String.class);
        return new ResponseParameters(response.getBody(), response.getStatusCodeValue(), null);
    }

    @Override
    public <T> ResponseParameters post(String endPoint, T value) {
        final String uri = BASE_URL + endPoint;
        //String post = restTemplate.postForObject(uri, value, String.class);
        HttpEntity<T> entity = new HttpEntity<T>(value);
        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, entity, String.class);
        return new ResponseParameters(response.getBody(), response.getStatusCodeValue(), null);
    }

    @Override
    public <T> ResponseParameters delete(String endPoint, String endPointParameter, Map<String, String> parameters, T value) {
        final String uri = BASE_URL + endPoint + endPointParameter;
//        restTemplate.delete(uri, parameters);
        HttpEntity<T> entity = new HttpEntity<T>(value);
        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.DELETE, entity, String.class, parameters);
        return new ResponseParameters(response.getBody(), response.getStatusCodeValue(), null);
    }
}
