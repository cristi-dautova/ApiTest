package test;

import entities.Category;
import entities.DeletedPet;
import entities.Pet;
import entities.Tag;
import model.BaseRestClient;
import model.ResponseParameters;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static constants.PhotoURL.PERSIAN_CAT_PHOTO;
import static constants.UrlConstants.PET_ENDPOINT;
import static model.HttpMethod.*;
import static utils.Serializer.deserializeJson;

public abstract class BasePetTests {

    protected BaseRestClient restClientImplementation;
    protected Pet pet;
    protected Map<String, String> headers = new HashMap<>();

    protected abstract BaseRestClient createBaseRestClient();

    @BeforeClass
    public void chooseMethod() {

        restClientImplementation = createBaseRestClient();

        Random random = new Random();

        Category category = new Category(1, "Cats");
        Tag tag = new Tag(1, "Persian cats");
        int id = random.nextInt(1000);
        System.out.println(id);
        pet = new Pet(String.valueOf(id),
                category, "Marsik",
                Collections.singletonList(PERSIAN_CAT_PHOTO),
                Collections.singletonList(tag), "available");

        headers.put("Accept", "application/json");
        headers.put("Content-type", "application/json");

    }

    @Test(priority = 1)
    public void addPet() throws IOException {
        ResponseParameters responseParameters = restClientImplementation.executeRequest(POST, PET_ENDPOINT, null, pet, headers, null);
        Pet petFromJson = deserializeJson(responseParameters.getBody(), Pet.class);
        Assert.assertEquals(responseParameters.getStatusCode(), 200);
        Assert.assertTrue(responseParameters.getHeaders().get("Content-Type").contains("application/json"));
        Assert.assertEquals(petFromJson.getId(), pet.getId());

    }

    @Test(priority = 2)
    public void updatePet() throws IOException {

        ResponseParameters responseParameters = restClientImplementation.executeRequest(PUT, PET_ENDPOINT, null, pet, headers, null);
        Pet petFromJson = deserializeJson(responseParameters.getBody(), Pet.class);
        Assert.assertEquals(responseParameters.getStatusCode(), 200);
        Assert.assertTrue(responseParameters.getHeaders().get("Content-Type").contains("application/json"));
        Assert.assertEquals(petFromJson.getId(), pet.getId());

    }

    @Test(priority = 3)
    public void getPetById() throws IOException {

        Map<String, String> parameters = new HashMap<>();
        parameters.put("petId", pet.getId());

        ResponseParameters responseParameters = restClientImplementation.executeRequest(GET, PET_ENDPOINT, parameters, pet, headers, null);
        Pet petFromJson = deserializeJson(responseParameters.getBody(), Pet.class);

        Assert.assertEquals(responseParameters.getStatusCode(), 200);
        Assert.assertTrue(responseParameters.getHeaders().get("Content-Type").contains("application/json"));
        Assert.assertEquals(petFromJson.getId(), pet.getId());

    }

    @Test(priority = 4)
    public void deletePet() throws IOException {

        Map<String, String> parameters = new HashMap<>();
        parameters.put("petId", pet.getId());

        ResponseParameters responseParameters = restClientImplementation.executeRequest(DELETE, PET_ENDPOINT, parameters, pet, headers, null);
        DeletedPet deletedPetFromJson = deserializeJson(responseParameters.getBody(), DeletedPet.class);

        Assert.assertEquals(responseParameters.getStatusCode(), 200);
        Assert.assertTrue(responseParameters.getHeaders().get("Content-Type").contains("application/json"));
        Assert.assertEquals(deletedPetFromJson.getMessage(), pet.getId());

    }
}
