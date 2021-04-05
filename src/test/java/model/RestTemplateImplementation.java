package model;

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
        return new ResponseParameters(restTemplate.getForObject(uri, String.class, parameters), 200, null);
    }

    @Override
    public <T> ResponseParameters put(String endPoint, T value) {
        final String uri = BASE_URL + endPoint;
        restTemplate.put(uri, value);
        return new ResponseParameters(null, 200, null);
    }

    @Override
    public <T> ResponseParameters post(String endPoint, T value) {
        final String uri = BASE_URL + endPoint;
        return new ResponseParameters(restTemplate.postForObject(uri, value, String.class), 200, null);
    }

    @Override
    public ResponseParameters delete(String endPoint, String endPointParameter, Map<String, String> parameters) {
        final String uri = BASE_URL + endPoint + endPointParameter;
        restTemplate.delete(uri, parameters);
        return new ResponseParameters(null, 200, null);
    }
}
