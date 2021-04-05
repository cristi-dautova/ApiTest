package model;

import io.restassured.response.ResponseOptions;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString()
@AllArgsConstructor
public class ResponseParameters {

    private String body;
    private int statusCode;
    private Object headers;

    public ResponseParameters(ResponseOptions responseOptions) {
        this(responseOptions.getBody().asString(), responseOptions.getStatusCode(), null);
        //Headers hid = responseOptions.getHeaders();
    }
}
