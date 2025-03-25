package ro.unibuc.hello.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LateRent {
    private String userId;
    private String gameId;
    private String rentDate;
}
