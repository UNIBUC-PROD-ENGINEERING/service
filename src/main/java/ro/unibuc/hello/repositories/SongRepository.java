package ro.unibuc.hello.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import ro.unibuc.hello.data.SongEntity;

import java.util.List;

public interface SongRepository extends MongoRepository<SongEntity, String> {
    List<SongEntity> findByPartyId(String partyId);
    List<SongEntity> findByTitle(String title);
    List<SongEntity> findByArtist(String artist);
}