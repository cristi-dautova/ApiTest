package utils;

import entities.Category;
import entities.Pet;
import entities.Tag;

import java.util.Collections;
import java.util.Random;

public class TestData {

    public Pet createPet(String animal, String breed, String name, String photoUrl, int categoryId, String status) {
        Random random = new Random();
        Category category = new Category(categoryId, animal);
        Tag tag = new Tag(categoryId, breed);
        int id = random.nextInt(1000);
        System.out.println(id);
        return new Pet(String.valueOf(id), category, name,
                Collections.singletonList(photoUrl),
                Collections.singletonList(tag), status);
    }

}
