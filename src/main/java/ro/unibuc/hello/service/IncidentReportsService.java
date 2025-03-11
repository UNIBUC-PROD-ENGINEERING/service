package ro.unibuc.hello.service;

import ro.unibuc.hello.dto.IncidentReportRequestDTO;
import ro.unibuc.hello.dto.IncidentReportResponseDTO;
import ro.unibuc.hello.exception.EntityNotFoundException;

import java.util.List;


public interface IncidentReportsService {
     List<IncidentReportResponseDTO> getAllIncidentReports();
    IncidentReportResponseDTO getIncidentReportById(Long id);
    IncidentReportResponseDTO createIncidentReport(IncidentReportRequestDTO incidentReport);
     IncidentReportResponseDTO updateIncidentReport(Long id, IncidentReportRequestDTO incidentReport)
            throws EntityNotFoundException;
     void deleteIncidentReport(Long id) throws EntityNotFoundException;
}
