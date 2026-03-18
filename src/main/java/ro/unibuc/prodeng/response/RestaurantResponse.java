package ro.unibuc.prodeng.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.DecimalMax;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantResponse {
    private String id;

    @NotBlank(message = "Numele este obligatoriu")
    private String name;

    @NotBlank(message = "Adresa este obligatorie")
    private String address;

    @NotBlank(message = "Tipul de bucatarie este obligatoriu")
    private String cuisineType;

    @DecimalMin(value = "1.0")
    @DecimalMax(value = "5.0")
    private Double rating;
}