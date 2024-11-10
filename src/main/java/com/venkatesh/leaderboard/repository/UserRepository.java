package com.venkatesh.leaderboard.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.venkatesh.leaderboard.model.User;

public interface UserRepository extends MongoRepository<User, String> {
    
}
