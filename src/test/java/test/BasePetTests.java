package test;

import entities.DeletedPet;
import entities.Pet;
import lombok.SneakyThrows;
import model.BaseRestClient;
import model.ResponseParameters;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static constants.PhotoURL.PERSIAN_CAT_PHOTO;
import static constants.UrlConstants.BASE_URL;
import static utils.TestData.createPet;

public abstract class BasePetTests {

    protected BaseRestClient restClientImplementation;
    protected Pet pet;
    protected Map<String, String> headers = new HashMap<>();

    protected abstract BaseRestClient createBaseRestClient();

    @BeforeClass
    public void chooseMethod() {

        restClientImplementation = createBaseRestClient();
        pet = createPet("Cats", "Persian cats", "Marsik", PERSIAN_CAT_PHOTO, 1, "available");

        headers.put("Accept", "application/json");
        headers.put("Content-type", "application/json");
    }

    @Test(priority = 1)
    @SneakyThrows
    public void addPet() {

        Map<String, String> parameters = new LinkedHashMap<>();
        parameters.put("version", "v2");
        parameters.put("animal", "pet");

        ResponseParameters responseParameters = restClientImplementation.post(BASE_URL, parameters, pet, headers, null, Pet.class);

        Assert.assertEquals(responseParameters.getStatusCode(), 200);
        Assert.assertTrue(responseParameters.getHeaders().get("Content-Type").contains("application/json"));
        Assert.assertEquals(((Pet) responseParameters.getEntity()).getId(), pet.getId());
    }

    @Test(priority = 2)
    @SneakyThrows
    public void updatePet() {

        Map<String, String> parameters = new LinkedHashMap<>();
        parameters.put("version", "v2");
        parameters.put("animal", "pet");

        ResponseParameters responseParameters = restClientImplementation.put(BASE_URL, parameters, pet, headers, null, Pet.class);

        Assert.assertEquals(responseParameters.getStatusCode(), 200);
        Assert.assertTrue(responseParameters.getHeaders().get("Content-Type").contains("application/json"));
        Assert.assertEquals(((Pet) responseParameters.getEntity()).getId(), pet.getId());
    }

    @Test(priority = 3)
    @SneakyThrows
    public void getPetById() {

        Map<String, String> parameters = new LinkedHashMap<>();
        parameters.put("version", "v2");
        parameters.put("animal", "pet");
        parameters.put("{petId}", pet.getId());

        ResponseParameters responseParameters = restClientImplementation.get(BASE_URL, parameters, pet, headers, null, Pet.class);

        Assert.assertEquals(responseParameters.getStatusCode(), 200);
        Assert.assertTrue(responseParameters.getHeaders().get("Content-Type").contains("application/json"));
        Assert.assertEquals(((Pet) responseParameters.getEntity()).getId(), pet.getId());
    }

    @Test(priority = 4)
    @SneakyThrows
    public void deletePet() {

        Map<String, String> parameters = new LinkedHashMap<>();
        parameters.put("version", "v2");
        parameters.put("animal", "pet");
        parameters.put("{petId}", pet.getId());

        ResponseParameters responseParameters = restClientImplementation.delete(BASE_URL, parameters, pet, headers, null, DeletedPet.class);

        Assert.assertEquals(responseParameters.getStatusCode(), 200);
        Assert.assertTrue(responseParameters.getHeaders().get("Content-Type").contains("application/json"));
        Assert.assertEquals(((DeletedPet) responseParameters.getEntity()).getMessage(), pet.getId());
    }
}
