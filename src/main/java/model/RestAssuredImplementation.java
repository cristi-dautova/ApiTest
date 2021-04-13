package model;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.response.ResponseOptions;
import io.restassured.specification.RequestSpecification;
import lombok.SneakyThrows;

import java.util.Map;

import static constants.UrlConstants.BASE_URL;
import static io.restassured.RestAssured.given;

public class RestAssuredImplementation extends BaseRestClient {

    protected static RequestSpecification requestSpecification;

    public RestAssuredImplementation() {
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.setBaseUri(BASE_URL);
        requestSpecBuilder.log(LogDetail.METHOD);
        requestSpecBuilder.log(LogDetail.URI);
        requestSpecification = requestSpecBuilder.build();
    }

    @SneakyThrows
    @Override
    public <T> ResponseParameters executeRequest(HttpMethod method, String endPoint, Map<String, String> pathParameters, T value, Map<String, String> headers, Map<String, String> queryParameters) {
        ResponseOptions responseOptions;
        RequestSpecification req = buildRequest(pathParameters, headers, queryParameters);
        switch (method) {
            case GET -> {
                String endPointWithBraces = wrapEndPointPathParameterWithBraces(pathParameters);
                responseOptions = req.get(endPoint + endPointWithBraces);
            }
            case DELETE -> {
                String endPointWithBraces = wrapEndPointPathParameterWithBraces(pathParameters);
                responseOptions = req.delete(endPoint + endPointWithBraces);
            }
            case POST -> {
                responseOptions = req.body(value).post(endPoint);
            }
            case PUT -> {
                responseOptions = req.body(value).put(endPoint);
            }
            default -> throw new IllegalStateException("Unexpected value: " + method);
        }
        return new ResponseParameters(responseOptions);
    }

    private RequestSpecification buildRequest(Map<String, String> pathParameters, Map<String, String> headers, Map<String, String> queryParameters) {
        RequestSpecification req = given(requestSpecification);
        if (pathParameters != null) {
            for (Map.Entry entry : pathParameters.entrySet()) {
                req = req.pathParam((String) entry.getKey(), entry.getValue());
            }
        }
        if (queryParameters != null) {
            for (Map.Entry entry : queryParameters.entrySet()) {
                req = req.pathParam((String) entry.getKey(), entry.getValue());
            }
        }
        if (headers != null) {
            for (Map.Entry entry : headers.entrySet()) {
                req = req.header((String) entry.getKey(), entry.getValue());
            }
        }
        return req;
    }
}
