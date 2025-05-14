package com.sourcegraph.petstore.controller;

import com.sourcegraph.petstore.model.Tag;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Path("/api/tags")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TagController {

    private final Map<Long, Tag> tagStore = new ConcurrentHashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);
    private final Random random = new Random();

    private final List<String> commonTags = Arrays.asList(
            "Friendly", "Active", "Playful", "Shy", "Trained", "Vaccinated",
            "Neutered", "Spayed", "Senior", "Puppy", "Kitten", "Rare",
            "Exotic", "Hypoallergenic", "Special Needs", "Rescue"
    );

    @GET
    public List<Tag> getAllTags() {
        return new ArrayList<>(tagStore.values());
    }

    @GET
    @Path("/{id}")
    public Tag getTagById(@PathParam("id") Long id) {
        return tagStore.get(id);
    }

    @POST
    public Response createTag(Tag tag) {
        Long id = idCounter.getAndIncrement();
        tag.setId(id);
        tagStore.put(id, tag);
        return Response.status(Response.Status.CREATED).entity(tag).build();
    }

    @PUT
    @Path("/{id}")
    public Tag updateTag(@PathParam("id") Long id, Tag tag) {
        tag.setId(id);
        tagStore.put(id, tag);
        return tag;
    }

    @DELETE
    @Path("/{id}")
    public Response deleteTag(@PathParam("id") Long id) {
        tagStore.remove(id);
        return Response.noContent().build();
    }

    @GET
    @Path("/random")
    public Tag getRandomTag() {
        return generateRandomTag();
    }

    @GET
    @Path("/random/{count}")
    public List<Tag> getRandomTags(@PathParam("count") int count) {
        return IntStream.range(0, count)
                .mapToObj(i -> generateRandomTag())
                .collect(Collectors.toList());
    }

    private Tag generateRandomTag() {
        Tag tag = new Tag();
        tag.setId(random.nextLong(1000));
        tag.setName(commonTags.get(random.nextInt(commonTags.size())));
        return tag;
    }
}