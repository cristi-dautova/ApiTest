package model;

import java.util.Map;

public interface BaseRestClient {

    public <T> ResponseParameters get(String endPont, String endPointParameter, Map<String, String> parameters, T value, Map<String, String> headers);

    public <T> ResponseParameters put(String endPoint, T value, Map<String, String> headers);

    public <T> ResponseParameters post(String endPont, T value, Map<String, String> headers);

    public <T> ResponseParameters delete(String endPont, String endPointParameter, Map<String, String> parameters, T value, Map<String, String> headers);
}
