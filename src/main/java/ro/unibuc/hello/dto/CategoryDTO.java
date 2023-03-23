package ro.unibuc.hello.dto;

import lombok.*;

import java.io.Serializable;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO implements Serializable {
    private String categoryName;
}
