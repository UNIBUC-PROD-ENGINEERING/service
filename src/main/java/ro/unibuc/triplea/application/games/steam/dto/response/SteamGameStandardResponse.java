package ro.unibuc.triplea.application.games.steam.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SteamGameStandardResponse {
    private String code;
    private String message;
    private String data;
}
