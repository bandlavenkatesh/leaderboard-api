# Leaderboard API

A RESTful API service built with Spring Boot to manage the leaderboard for a coding platform using MongoDB. This API allows for CRUD operations on user registrations, score updates, and badge assignments based on score levels.

## Features
- Register users for a contest and assign scores.
- Automatically assign badges based on user scores.
- Retrieve leaderboard sorted by scores.
- Error handling and validation for all operations.

## Technologies Used
- **Spring Boot** for REST API development
- **MongoDB** for data persistence
- **JUnit** and **Mockito** for testing
- **Lombok** for reducing boilerplate code

## Requirements
- Java 17 or later
- MongoDB (running locally on default port `27017`)
- Gradle for building and running the application

## Setup Instructions

1. **Clone the repository**:
   ```bash
   git clone https://github.com/bandlavenkatesh/leaderboard-api.git
   cd leaderboard-api

## Postman Collection

You can use the Postman collection to test API endpoints. Click the button below to import the collection into your Postman workspace.

[![Run in Postman](https://run.pstmn.io/button.svg)]([<img src="https://run.pstmn.io/button.svg" alt="Run In Postman" style="width: 128px; height: 32px;">](https://app.getpostman.com/run-collection/39627411-0f2002f7-8eb2-4be3-b897-2deb026b0bd2?action=collection%2Ffork&source=rip_markdown&collection-url=entityId%3D39627411-0f2002f7-8eb2-4be3-b897-2deb026b0bd2%26entityType%3Dcollection%26workspaceId%3D87ac921e-d71e-48fe-a7db-55699dc9528e))

