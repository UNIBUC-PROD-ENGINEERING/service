package ro.unibuc.hello.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import ro.unibuc.hello.data.AuthorEntity;
import ro.unibuc.hello.service.AuthorService;
import ro.unibuc.hello.dto.AuthorCreationRequestDto;
import ro.unibuc.hello.dto.AuthorDeleteRequestDto;
import ro.unibuc.hello.dto.UpdateAuthorRequestDto;

@Controller
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @GetMapping("/authors")
    @ResponseBody
    public List<AuthorEntity> getAllAuthors(){
        return authorService.getAllAuthors();
    }
    
    @PostMapping("/authors")
    @ResponseBody
    public ResponseEntity<AuthorEntity> createAuthor(@RequestBody AuthorCreationRequestDto authorCreationRequestDto) {
        var newAuthor = authorService.saveAuthor(authorCreationRequestDto);
        return ResponseEntity.ok(newAuthor);
    }

    @PatchMapping("/authors/{id}")
    @ResponseBody
    public ResponseEntity<AuthorEntity> updateAuthor(@PathVariable String id,
            @RequestBody UpdateAuthorRequestDto updateAuthorRequestDto) {
        var updatedAuthor = authorService.updateAuthor(id, updateAuthorRequestDto);
        return ResponseEntity.ok(updatedAuthor);
    }

    @DeleteMapping("/authors") 
    @ResponseBody
    public ResponseEntity<String> deleteAuthor(@RequestBody AuthorDeleteRequestDto authorDeleteRequestDto) {
        authorService.deleteAuthor(authorDeleteRequestDto);
        return ResponseEntity.ok("Author deleted successfully");
    }
}
