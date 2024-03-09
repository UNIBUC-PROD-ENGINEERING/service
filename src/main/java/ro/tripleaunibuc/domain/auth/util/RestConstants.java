package ro.tripleaunibuc.domain.auth.util;

public class RestConstants {

    public static final String AUTH_HEADER = "Authorization";

    public static final String API_URL = "/api/v1";

    public static final String AUTH_URL = API_URL + "/auth";
    public static final String REGISTER_URL = API_URL + "/register";
    public static final String REFRESH_TOKEN_URL = API_URL + "/refresh";
    public static final String TODO_URL = API_URL + "/users/{username}/todos";
    public static final String WELCOME_URL = API_URL + "/hello/{name}";

    public static final String SECURED_API_URL = "/api/v1/**";

}
