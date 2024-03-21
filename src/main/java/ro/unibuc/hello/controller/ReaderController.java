package ro.unibuc.hello.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import ro.unibuc.hello.data.ReaderEntity;
import ro.unibuc.hello.service.ReaderService;
import ro.unibuc.hello.dto.ReaderCreationRequestDto;

import ro.unibuc.hello.dto.ReaderUpdateRequestDto;

@Controller
public class ReaderController {

    @Autowired
    private ReaderService readerService;

    @GetMapping("/readers")
    @ResponseBody
    private List<ReaderEntity> getReaders() {
        return readerService.getAllReaders();
    }

    @PostMapping("/readers")
    @ResponseBody
    public ResponseEntity<ReaderEntity> createReader(@RequestBody ReaderCreationRequestDto readerCreationRequestDto) {
        var newReader = readerService.saveReader(readerCreationRequestDto);
        return ResponseEntity.ok(newReader);
    }

    @PatchMapping("/readers/{id}")
    @ResponseBody
    public ResponseEntity<ReaderEntity> updateReader(@PathVariable String id,
            @RequestBody ReaderUpdateRequestDto readerUpdateRequestDto) {
        var updatedReader = readerService.updateReader(id, readerUpdateRequestDto);
        return ResponseEntity.ok(updatedReader);
    }

    @DeleteMapping("readers/records/{readerId}")
    public ResponseEntity<String> deleteReaderAndReadingRecords(@PathVariable String readerId) {
        readerService.deleteReaderAndReadingRecords(readerId);
        return ResponseEntity.ok("Reader and associated reading records deleted successfully");
    }
}
