package test;

import model.BaseRestClient;
import model.HTTPClientImplementation;

public class PetTestsHTTP extends BasePetTests {

    @Override
    protected BaseRestClient createBaseRestClient() {
        return new HTTPClientImplementation();
    }
}
