package ro.unibuc.triplea.application.reviews.steam.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SteamGameReviewStandardResponse {
    private String code;
    private String message;
    private String data;
}
