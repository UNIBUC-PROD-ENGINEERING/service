package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import ro.unibuc.hello.data.ReadingRecordEntity;
import ro.unibuc.hello.dto.ReadingRecordCreationRequestDto;
import ro.unibuc.hello.service.ReadingRecordService;

@Controller
public class ReadingRecordController {
    @Autowired
    private ReadingRecordService readingRecordService;

    @PostMapping("/readingrecords")
    @ResponseBody
    public ResponseEntity<ReadingRecordEntity> createReadingRecord(
            @RequestBody ReadingRecordCreationRequestDto readingRecordCreationRequestDto) {

        var newReadingRecord = readingRecordService.saveReadingRecord(readingRecordCreationRequestDto);
        return ResponseEntity.ok(newReadingRecord);
    }
}
