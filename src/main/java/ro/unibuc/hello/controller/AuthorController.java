package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import ro.unibuc.hello.data.AuthorEntity;
import ro.unibuc.hello.service.AuthorService;
import ro.unibuc.hello.dto.AuthorCreationRequestDto;

@Controller
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @PostMapping("/authors")
    @ResponseBody
    public ResponseEntity<AuthorEntity> createAuthor(@RequestBody AuthorCreationRequestDto authorCreationRequestDto) {
        var newAuthor = authorService.saveAuthor(authorCreationRequestDto.getName(),
                authorCreationRequestDto.getNationality());
        return ResponseEntity.ok(newAuthor);
    }
}
