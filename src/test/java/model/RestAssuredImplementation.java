package model;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.ResponseOptions;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static utils.UrlConstants.BASE_URL;

public class RestAssuredImplementation implements BaseRestClient {

    protected static RequestSpecification requestSpecification;

    public RestAssuredImplementation() {
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.setBaseUri(BASE_URL);
        requestSpecBuilder.log(LogDetail.METHOD);
        requestSpecBuilder.log(LogDetail.URI);
        requestSpecification = requestSpecBuilder.build();
    }

    @Override
    public <T> ResponseParameters get(String endPont, String endPointParameter, Map<String, String> parameters, T value, Map<String, String> headers) {
        RequestSpecification req = given(requestSpecification);
        for (Map.Entry entry : parameters.entrySet()) {
            req = req.pathParam((String) entry.getKey(), entry.getValue());
        }
        for (Map.Entry entry : headers.entrySet()) {
            req = req.header((String) entry.getKey(), entry.getValue());
        }
        ResponseOptions responseOptions = req.get(endPont + endPointParameter);

        return new ResponseParameters(responseOptions);
    }

    @Override
    public <T> ResponseParameters post(String endPoint, T value, Map<String, String> headers) {
        RequestSpecification req = given(requestSpecification);
        for (Map.Entry entry : headers.entrySet()) {
            req = req.header((String) entry.getKey(), entry.getValue());
        }
        return new ResponseParameters(req
                .body(value)
                .post(endPoint));
    }

    @Override
    public <T> ResponseParameters put(String endPoint, T value, Map<String, String> headers) {
        RequestSpecification req = given(requestSpecification);
        for (Map.Entry entry : headers.entrySet()) {
            req = req.header((String) entry.getKey(), entry.getValue());
        }
        return new ResponseParameters(req
                .body(value)
                .put(endPoint));
    }

    @Override
    public <T> ResponseParameters delete(String endPont, String endPointParameter, Map<String, String> parameters, T value, Map<String, String> headers) {
        RequestSpecification req = given(requestSpecification);
        for (Map.Entry entry : parameters.entrySet()) {
            req = req.pathParam((String) entry.getKey(), entry.getValue());
        }
        for (Map.Entry entry : headers.entrySet()) {
            req = req.header((String) entry.getKey(), entry.getValue());
        }
        ResponseOptions responseOptions = req.delete(endPont + endPointParameter);
        return new ResponseParameters(responseOptions);
    }
}
