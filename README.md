# Quarkus Pet Store API

This is a Quarkus implementation of the Pet Store API. It's equivalent to the Spring Boot implementation but uses Quarkus framework instead.

## Requirements

- JDK 17 or higher
- Gradle 8.x

## Building and Running

You can build and run the application using Gradle:

```bash
./gradlew quarkusDev
```

This will start the application in development mode. The API will be accessible at http://localhost:8080/api

## Features

- RESTful API for pets, categories, and tags
- OpenAPI client generation
- Basic security configuration
- SPA support

## API Endpoints

- `/api/pets/random` - Get a random pet
- `/api/pets/random/{count}` - Get multiple random pets
- `/api/categories/random` - Get a random category
- `/api/categories/random/{count}` - Get multiple random categories
- `/api/tags/random` - Get a random tag
- `/api/tags/random/{count}` - Get multiple random tags

The application also supports standard CRUD operations on categories and tags.

## Building a Native Executable

To build a native executable:

```bash
./gradlew build -Dquarkus.package.type=native
```

You can then run the native executable with:

```bash
./build/sourcegraph-quarkus-pet-store-runner
```