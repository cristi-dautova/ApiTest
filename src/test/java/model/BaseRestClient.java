package model;

import java.util.Map;

public interface BaseRestClient {

    public ResponseParameters get(String endPont, String endPointParameter, Map<String, String> parameters);

    public <T> ResponseParameters put(String endPoint, T value);

    public <T> ResponseParameters post(String endPont, T value);

    public ResponseParameters delete(String endPont, String endPointParameter, Map<String, String> parameters);
}
