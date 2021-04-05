package test;

import model.BaseRestClient;
import model.RestTemplateImplementation;

public class PetTestsTemplate extends BasePetTests {

    @Override
    protected BaseRestClient createBaseRestClient() {
        return new RestTemplateImplementation();
    }
}
