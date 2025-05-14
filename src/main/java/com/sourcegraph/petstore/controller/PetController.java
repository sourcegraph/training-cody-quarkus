package com.sourcegraph.petstore.controller;

import com.sourcegraph.petstore.model.Category;
import com.sourcegraph.petstore.model.Pet;
import com.sourcegraph.petstore.model.Tag;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Path("/api/pets")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PetController {

    private final Random random = new Random();

    private final List<String> petNames = Arrays.asList(
            "Buddy", "Max", "Charlie", "Lucy", "Cooper", "Bella", "Luna", "Daisy",
            "Rocky", "Sadie", "Milo", "Bailey", "Jack", "Oliver", "Chloe", "Pepper"
    );

    private final List<String> categories = Arrays.asList(
            "Dog", "Cat", "Bird", "Fish", "Reptile", "Rodent", "Exotic"
    );

    private final List<String> tagNames = Arrays.asList(
            "Friendly", "Playful", "Trained", "Young", "Adult", "Senior",
            "Vaccinated", "Neutered", "Spayed", "Rescue", "Purebred", "Hypoallergenic"
    );

    @GET
    @Path("/random")
    public Pet getRandomPet() {
        return generateRandomPet();
    }

    @GET
    @Path("/random/{count}")
    public List<Pet> getRandomPets(@PathParam("count") int count) {
        return IntStream.range(0, count)
                .mapToObj(i -> generateRandomPet())
                .collect(Collectors.toList());
    }

    private Pet generateRandomPet() {
        // Create pet with required fields
        Pet pet = new Pet();
        pet.setId(random.nextLong(10000));
        pet.setName(getRandomElement(petNames));
        pet.setPhotoUrls(generateRandomPhotoUrls());

        // Add optional fields
        pet.setCategory(generateRandomCategory());
        pet.setTags(generateRandomTags());
        pet.setStatus(getRandomStatus());

        return pet;
    }

    private Category generateRandomCategory() {
        Category category = new Category();
        category.setId(random.nextLong(100));
        category.setName(getRandomElement(categories));
        return category;
    }

    private List<String> generateRandomPhotoUrls() {
        int count = random.nextInt(3) + 1; // 1-3 photos
        return IntStream.range(0, count)
                .mapToObj(i -> "https://example.com/pet-photos/" + UUID.randomUUID() + ".jpg")
                .collect(Collectors.toList());
    }

    private List<Tag> generateRandomTags() {
        int count = random.nextInt(4); // 0-3 tags
        return IntStream.range(0, count)
                .mapToObj(i -> {
                    Tag tag = new Tag();
                    tag.setId(random.nextLong(100));
                    tag.setName(getRandomElement(tagNames));
                    return tag;
                })
                .collect(Collectors.toList());
    }

    private Pet.StatusEnum getRandomStatus() {
        Pet.StatusEnum[] statuses = Pet.StatusEnum.values();
        return statuses[random.nextInt(statuses.length)];
    }

    private <T> T getRandomElement(List<T> list) {
        return list.get(random.nextInt(list.size()));
    }
}