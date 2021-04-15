package model;

import java.util.Map;

import static model.HttpMethod.*;

public abstract class BaseRestClient {

    public <T> ResponseParameters post(String url, Map<String, String> pathParameters, T value, Map<String, String> headers, Map<String, String> queryParameters, Class<T> clazz) {
        return execute(POST, url, pathParameters, value, headers, queryParameters, clazz);
    }

    public <T> ResponseParameters get(String url, Map<String, String> pathParameters, T value, Map<String, String> headers, Map<String, String> queryParameters, Class<T> clazz) {
        return execute(GET, url, pathParameters, value, headers, queryParameters, clazz);
    }

    public <T> ResponseParameters put(String url, Map<String, String> pathParameters, T value, Map<String, String> headers, Map<String, String> queryParameters, Class<T> clazz) {
        return execute(PUT, url, pathParameters, value, headers, queryParameters, clazz);
    }

    public <T1, T2> ResponseParameters delete(String url, Map<String, String> pathParameters, T1 value, Map<String, String> headers, Map<String, String> queryParameters, Class<T2> clazz) {
        return execute(DELETE, url, pathParameters, value, headers, queryParameters, clazz);
    }

    private <T1, T2> ResponseParameters execute(HttpMethod method, String url, Map<String, String> pathParameters, T1 value, Map<String, String> headers, Map<String, String> queryParameters, Class<T2> clazz) {
        Object request = buildRequest(method, url, pathParameters, queryParameters, headers, value);
        return executeRequest(request, clazz);
    }

    protected abstract <T> Object buildRequest(HttpMethod method, String url, Map<String, String> pathParameters, Map<String, String> queryParameters, Map<String, String> headers, T value);

    protected abstract <T> ResponseParameters executeRequest(Object request, Class<T> clazz);
}
