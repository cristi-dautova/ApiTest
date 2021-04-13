package model;

import java.util.Map;

public abstract class BaseRestClient {

    public abstract <T> ResponseParameters executeRequest(HttpMethod method, String endPont, Map<String, String> pathParameters, T value, Map<String, String> headers, Map<String, String> queryParameters);

    protected String wrapEndPointPathParameterWithBraces(Map<String, String> pathParameters) {
        StringBuilder endPointWithBraces = new StringBuilder();
        if (pathParameters != null) {
            for (Map.Entry entry : pathParameters.entrySet()) {
                endPointWithBraces.append("/{").append((String) entry.getKey()).append("}");
            }
        }
        return endPointWithBraces.toString();
    }
}
