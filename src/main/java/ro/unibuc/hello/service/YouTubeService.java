package ro.unibuc.hello.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

@Service
public class YouTubeService {
    private static final String API_KEY = "AIzaSyCyhHqVWjKp-mOUoF05q3OjU-21KNizZJg"; // Replace with your actual API key
    private static final String SEARCH_URL = "https://www.googleapis.com/youtube/v3/search";
    private static final Logger logger = Logger.getLogger(YouTubeService.class.getName());

    public String searchYouTube(String title, String artist) {
        RestTemplate restTemplate = new RestTemplate();

        // Clean and encode the query for better search results
        String query = URLEncoder.encode(title + " " + artist, StandardCharsets.UTF_8);

        String url = UriComponentsBuilder.fromUriString(SEARCH_URL)
                .queryParam("part", "snippet")
                .queryParam("q", query)  // Encoded query
                .queryParam("key", API_KEY)
                .queryParam("type", "video")
                .queryParam("maxResults", 1)
                .toUriString();

        logger.info("Constructed URL: " + url);  // Log the constructed URL

        try {
            YouTubeResponse response = restTemplate.getForObject(url, YouTubeResponse.class);

            // Log the full response for debugging purposes
            if (response != null) {
                logger.info("YouTube API Response: " + response);
            }

            if (response != null && response.getItems() != null && !response.getItems().isEmpty()) {
                return "https://www.youtube.com/watch?v=" + response.getItems().get(0).getId().getVideoId();
            } else {
                logger.warning("No results found for query: " + query);
            }
        } catch (Exception e) {
            logger.severe("Error fetching YouTube data: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}
