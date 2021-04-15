package model;

import org.springframework.http.HttpEntity;

public class RequestParametersRestTemplate<T> {

    public HttpMethod method;
    public String uri;
    public HttpEntity<T> entity;

    public RequestParametersRestTemplate(HttpMethod method, String uri, HttpEntity<T> entity) {
        this.method = method;
        this.uri = uri;
        this.entity = entity;
    }
}
