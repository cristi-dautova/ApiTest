package test;

import entities.Category;
import entities.Pet;
import entities.Status;
import entities.Tag;
import model.BaseRestClient;
import model.ResponseParameters;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static utils.PhotoURL.PERSIAN_CAT_PHOTO;
import static utils.UrlConstants.PET_ENDPOINT;
import static utils.UrlConstants.PET_ID_ENDPOINT;

public abstract class BasePetTests {

    BaseRestClient restClientImplementation;
    Pet pet;

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
                Collections.singletonList(tag), Status.available);

    }

    @Test(priority = 1)
    public void addPet() {
        ResponseParameters responseParameters = restClientImplementation.post(PET_ENDPOINT, pet);
        Assert.assertEquals(responseParameters.getStatusCode(), 200);
    }

    @Test(priority = 2)
    public void updatePet() {
        ResponseParameters responseParameters = restClientImplementation.put(PET_ENDPOINT, pet);
        Assert.assertEquals(responseParameters.getStatusCode(), 200);
    }

    @Test(priority = 3)
    public void getPetsById() {

        Map<String, String> parameters = new HashMap<>();
        parameters.put("petId", pet.getId());

        ResponseParameters responseParameters = restClientImplementation.get(PET_ENDPOINT, PET_ID_ENDPOINT, parameters);
        Assert.assertEquals(responseParameters.getStatusCode(), 200);

    }

    @Test(priority = 4)
    public void deletePet() {

        Map<String, String> parameters = new HashMap<>();
        parameters.put("petId", pet.getId());

        ResponseParameters responseParameters = restClientImplementation.delete(PET_ENDPOINT, PET_ID_ENDPOINT, parameters, pet);
        Assert.assertEquals(responseParameters.getStatusCode(), 200);
    }
}
