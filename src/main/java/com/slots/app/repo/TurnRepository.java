package com.slots.app.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.slots.app.model.Turn;

@Repository
public interface TurnRepository extends MongoRepository<Turn, String> {}
