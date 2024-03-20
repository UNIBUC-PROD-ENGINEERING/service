package ro.unibuc.triplea.application.favorites.steam.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class SteamGameFavoriteRequest {

    private int gameSteamId;
}
