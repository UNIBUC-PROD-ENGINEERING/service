package ro.unibuc.hello.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import ro.unibuc.hello.data.AuthorEntity;
import ro.unibuc.hello.data.ReaderEntity;
import ro.unibuc.hello.service.ReaderService;
import ro.unibuc.hello.dto.AuthorCreationRequestDto;
import ro.unibuc.hello.dto.Greeting;
import ro.unibuc.hello.dto.ReaderCreationRequestDto;

@Controller
public class ReaderController {

    @Autowired
    private ReaderService readerService;

    @GetMapping("/readers")
    @ResponseBody
    private List<ReaderEntity> getReaders(){
        return readerService.getAllReaders();
    }

    @PostMapping("/readers")
    @ResponseBody
    public ResponseEntity<ReaderEntity> createReader(@RequestBody ReaderCreationRequestDto readerCreationRequestDto) {
        var newReader = readerService.saveReader(readerCreationRequestDto.getName(),
                readerCreationRequestDto.getNationality(),
                readerCreationRequestDto.getEmail(),
                readerCreationRequestDto.getPhoneNumber(),
                readerCreationRequestDto.getBirthDate(),
                readerCreationRequestDto.getRegistrationDate());
        return ResponseEntity.ok(newReader);
    }

    @PatchMapping("/readers/{id}")
    @ResponseBody
    public ResponseEntity<ReaderEntity> updateReader(@PathVariable String id, 
                                                     @RequestBody ReaderCreationRequestDto readerCreationRequestDto) {
        var updatedReader = readerService.updateReader(id,
                                readerCreationRequestDto.getEmail(),
                                readerCreationRequestDto.getPhoneNumber());
        return ResponseEntity.ok(updatedReader);
    }


}
