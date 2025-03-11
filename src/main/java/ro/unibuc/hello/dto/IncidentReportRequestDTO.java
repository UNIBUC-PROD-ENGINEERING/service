package ro.unibuc.hello.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ro.unibuc.hello.enums.SeverityEnum;
import ro.unibuc.hello.enums.StatusEnum;

public record IncidentReportRequestDTO(
        @NotBlank String title,
        @NotBlank String description,
        @NotNull SeverityEnum severity,
        @NotNull StatusEnum status) {
}
