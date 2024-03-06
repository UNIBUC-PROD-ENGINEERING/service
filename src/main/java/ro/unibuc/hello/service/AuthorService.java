package ro.unibuc.hello.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ro.unibuc.hello.data.AuthorEntity;
import ro.unibuc.hello.data.AuthorRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    public AuthorEntity saveAuthor(String name, String nationality) {
        log.debug("Creating a new author '{}' with nationality '{}'", name, nationality);
        var authorEntity = AuthorEntity.builder().name(name).nationality(nationality).build();
        return authorRepository.save(authorEntity);
    }

}
