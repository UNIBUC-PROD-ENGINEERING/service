package ro.unibuc.triplea.application.favorites.steam.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SteamGameFavoriteResponse {
    private String userName;
    private int gameSteamId;
    private String gameName;

}
