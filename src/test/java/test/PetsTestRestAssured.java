package test;

import model.BaseRestClient;
import model.RestAssuredImplementation;

public class PetsTestRestAssured extends BasePetTests {
    @Override
    protected BaseRestClient createBaseRestClient() {
        return new RestAssuredImplementation();
    }
}
