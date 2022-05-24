package com.slots.app.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.slots.app.model.Game;

@Repository
public interface GameRepository extends MongoRepository<Game, String> {}
