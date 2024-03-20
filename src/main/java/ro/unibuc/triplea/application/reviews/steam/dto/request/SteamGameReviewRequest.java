package ro.unibuc.triplea.application.reviews.steam.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SteamGameReviewRequest {

    private int gameSteamId;
    private String reviewContent;
}
