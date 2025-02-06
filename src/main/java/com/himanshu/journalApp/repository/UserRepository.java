package com.himanshu.journalApp.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.himanshu.journalApp.entity.User;

public interface UserRepository extends MongoRepository<User, ObjectId> {
    
    User findByUsername(String username);

    User deleteByUsername(String username);
}
