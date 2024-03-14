package ro.unibuc.hello.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ro.unibuc.hello.dto.tmdb.CastDto;
import ro.unibuc.hello.dto.tmdb.MovieApiDto;
import ro.unibuc.hello.dto.tmdb.PagedApiResponseDto;

import java.io.IOException;

@Component
public class TheMovieDbClient {
    private final OkHttpClient client;
    private final ObjectMapper objectMapper;

    @Value("${tmdb.connection.token}")
    private String bearerToken;

    public TheMovieDbClient() {
        this.client = new OkHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    public PagedApiResponseDto<MovieApiDto> searchMovie(String name) {
        return searchMovie(name, 1);
    }

    public PagedApiResponseDto<MovieApiDto> searchMovie(String name, int page) {
        final String url = "https://api.themoviedb.org/3/search/movie?query=" + name + "&page=" + page;
        final Request request = addHeadersToRequest(new Request.Builder().url(url).get());

        final String responseBody = executeCallAndGetResponseString(request);
        final JavaType type = objectMapper.getTypeFactory().constructParametricType(PagedApiResponseDto.class, MovieApiDto.class);
        final PagedApiResponseDto<MovieApiDto> pagedApiResponseDto;

        try {
            pagedApiResponseDto = objectMapper.readValue(responseBody, type);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return pagedApiResponseDto;
    }

    public CastDto getCastByMovieId(Long tmdbId) {
        final String url = "https://api.themoviedb.org/3/movie/" + tmdbId + "/credits";
        final Request request = addHeadersToRequest(new Request.Builder().url(url).get());

        final String responseBody = executeCallAndGetResponseString(request);

        try {
            return objectMapper.readValue(responseBody, CastDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public MovieApiDto getMovieById(Long tmdbId) {
        final String url = "https://api.themoviedb.org/3/movie/" + tmdbId + "?language=en-US";
        final Request request = addHeadersToRequest(new Request.Builder().url(url).get());

        final String responseBody = executeCallAndGetResponseString(request);

        try {
            return objectMapper.readValue(responseBody, MovieApiDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private String executeCallAndGetResponseString(Request request) {
        try (final Response response = client.newCall(request).execute()) {
            if (response.body() == null) {
                throw new RuntimeException("Response body is null");
            }
            return response.body().string();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Request addHeadersToRequest(Request.Builder requestBuilder) {
        return requestBuilder
                .addHeader("Authorization", "Bearer " + bearerToken)
                .addHeader("accept", "application/json")
                .build();
    }
}
