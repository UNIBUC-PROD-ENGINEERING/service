package ro.unibuc.prodeng.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record AddToCartRequest(
    @NotBlank(message = "Component ID is required") String componentId,
    @Min(value = 1, message = "Quantity must be at least 1") int quantity
) {}