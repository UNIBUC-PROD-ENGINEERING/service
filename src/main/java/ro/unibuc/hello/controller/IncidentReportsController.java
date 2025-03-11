package ro.unibuc.hello.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.hello.dto.IncidentReportRequestDTO;
import ro.unibuc.hello.dto.IncidentReportResponseDTO;
import ro.unibuc.hello.exception.EntityNotFoundException;
import ro.unibuc.hello.service.IncidentReportsService;

import java.util.List;

@RestController
@RequestMapping("/incidents")
public class IncidentReportsController {
    private final IncidentReportsService incidentReportsService;

    public IncidentReportsController(IncidentReportsService incidentReportsService) {
        this.incidentReportsService = incidentReportsService;
    }

    @GetMapping
    public ResponseEntity<List<IncidentReportResponseDTO>> getAllIncidentReports(){
        return ResponseEntity.ok(incidentReportsService.getAllIncidentReports());
    }

    @GetMapping("/{id}")
    public ResponseEntity<IncidentReportResponseDTO> getIncidentReportById(@PathVariable Long id) throws EntityNotFoundException {
        return ResponseEntity.ok(incidentReportsService.getIncidentReportById(id));
    }

    @PostMapping
    public ResponseEntity<IncidentReportResponseDTO> addIncidentReport(@Valid @RequestBody IncidentReportRequestDTO incidentReport){
        return ResponseEntity.ok(incidentReportsService.createIncidentReport(incidentReport));
    }

    @PutMapping("/{id}")
    public ResponseEntity<IncidentReportResponseDTO> updateIncidentReport(@PathVariable Long id, @Valid @RequestBody IncidentReportRequestDTO incidentReport) throws EntityNotFoundException {
        return ResponseEntity.ok(incidentReportsService.updateIncidentReport(id, incidentReport));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIncidentReport(@PathVariable Long id) throws EntityNotFoundException {
        incidentReportsService.deleteIncidentReport(id);
        return ResponseEntity.noContent().build();
    }
}
