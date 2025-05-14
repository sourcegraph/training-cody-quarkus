package com.sourcegraph.petstore.controller;

import com.sourcegraph.petstore.model.Category;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Path("/api/categories")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CategoryController {

    private final Map<Long, Category> categoryStore = new ConcurrentHashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);
    private final Random random = new Random();

    private final List<String> petCategories = Arrays.asList(
            "Dog", "Cat", "Bird", "Fish", "Reptile", "Amphibian", "Small Mammal",
            "Exotic", "Farm Animal", "Insect", "Arachnid"
    );

    @GET
    public List<Category> getAllCategories() {
        return new ArrayList<>(categoryStore.values());
    }

    @GET
    @Path("/{id}")
    public Category getCategoryById(@PathParam("id") Long id) {
        return categoryStore.get(id);
    }

    @POST
    public Response createCategory(Category category) {
        Long id = idCounter.getAndIncrement();
        category.setId(id);
        categoryStore.put(id, category);
        return Response.status(Response.Status.CREATED).entity(category).build();
    }

    @PUT
    @Path("/{id}")
    public Category updateCategory(@PathParam("id") Long id, Category category) {
        category.setId(id);
        categoryStore.put(id, category);
        return category;
    }

    @DELETE
    @Path("/{id}")
    public Response deleteCategory(@PathParam("id") Long id) {
        categoryStore.remove(id);
        return Response.noContent().build();
    }

    @GET
    @Path("/random")
    public Category getRandomCategory() {
        return generateRandomCategory();
    }

    @GET
    @Path("/random/{count}")
    public List<Category> getRandomCategories(@PathParam("count") int count) {
        return IntStream.range(0, count)
                .mapToObj(i -> generateRandomCategory())
                .collect(Collectors.toList());
    }

    private Category generateRandomCategory() {
        Category category = new Category();
        category.setId(random.nextLong(100));
        category.setName(petCategories.get(random.nextInt(petCategories.size())));
        return category;
    }
}