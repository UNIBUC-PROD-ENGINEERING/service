package ro.unibuc.hello.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import ro.unibuc.hello.enums.SeverityEnum;
import ro.unibuc.hello.enums.StatusEnum;

import java.time.LocalDateTime;

public record IncidentReportResponseDTO(
        @PositiveOrZero Long id,
        @NotBlank String title,
        @NotBlank String description,
        @NotNull SeverityEnum severity,
        @NotNull StatusEnum status,
        @NotBlank LocalDateTime createdAt,
        @NotBlank LocalDateTime updatedAt) {
}
