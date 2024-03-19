package ro.unibuc.triplea.application.games.steam.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SteamGameResponse {
    private int gameSteamId;
    private String gameName;
}
