# Leaderboard API

This is a Spring Boot REST API for managing a leaderboard in a coding platform. It allows users to register, update scores, and view the leaderboard. MongoDB is used for data persistence.

## Features

- CRUD operations for users on the leaderboard
- Badges assigned based on score (e.g., Code Ninja, Code Champ, Code Master)
- Leaderboard sorted by score

## Requirements

- Java 17
- MongoDB
- Gradle (for build automation)

## Getting Started

### Prerequisites

1. **Java 17** installed on your machine.
2. **MongoDB** installed and running locally on `localhost:27017`.

### Setup

1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/leaderboard-api.git
   cd leaderboard-api
