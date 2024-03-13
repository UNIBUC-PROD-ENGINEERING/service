package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import ro.unibuc.hello.data.ReaderEntity;
import ro.unibuc.hello.service.ReaderService;
import ro.unibuc.hello.dto.ReaderCreationRequestDto;

@Controller
public class ReaderController {

    @Autowired
    private ReaderService readerService;

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


}
