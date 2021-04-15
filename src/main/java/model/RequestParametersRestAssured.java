package model;

import io.restassured.specification.RequestSpecification;

public class RequestParametersRestAssured {

    public HttpMethod method;
    public RequestSpecification requestSpecification;

    public RequestParametersRestAssured(HttpMethod method, RequestSpecification requestSpecification) {
        this.method = method;
        this.requestSpecification = requestSpecification;
    }
}
