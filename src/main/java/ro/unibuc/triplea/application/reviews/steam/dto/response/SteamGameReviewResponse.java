package ro.unibuc.triplea.application.reviews.steam.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SteamGameReviewResponse {
    private int gameSteamId;
    private String gameName;
    private String userName;
    private String reviewContent;
}
