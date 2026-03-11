package ro.unibuc.prodeng.request;

import jakarta.validation.constraints.NotBlank;


public record UpdateUserRequest(
    @NotBlank String name,
    String group
) {}