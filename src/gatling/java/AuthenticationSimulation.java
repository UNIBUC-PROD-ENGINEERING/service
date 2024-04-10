import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.gatling.core.config.GatlingConfiguration;
import io.gatling.http.Predef;
import io.gatling.http.protocol.HttpProtocolBuilder;
import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.HttpDsl;
import ro.unibuc.triplea.application.auth.dto.request.RegisterRequest;
import ro.unibuc.triplea.domain.auth.model.enums.Gender;
import ro.unibuc.triplea.domain.auth.model.enums.Role;

import java.util.Arrays;
import java.util.function.Function;

import static ro.unibuc.triplea.application.auth.fixtures.AuthenticationFixtures.generateRandomString;


public class AuthenticationSimulation extends Simulation {

    private final HttpProtocolBuilder httpProtocol = Predef.http(GatlingConfiguration.loadForTest())
            .baseUrl("http://localhost:8080");

    static final class Templates {
        public static final Function<Session, String> template = session -> {
            ObjectMapper objectMapper = new ObjectMapper();

            RegisterRequest registerRequest = RegisterRequest.builder()
                    .firstname(generateRandomString())
                    .lastname(generateRandomString())
                    .email(generateRandomString())
                    .password(generateRandomString())
                    .role(Arrays.stream(Role.values()).findAny().get())
                    .gender(Gender.MALE)
                    .address(generateRandomString())
                    .mobileNumber(generateRandomString())
                    .build();
            try {
                return objectMapper.writeValueAsString(registerRequest);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        };
    }

    private final ScenarioBuilder scenario = CoreDsl.scenario("Register - Load Testing")
            .exec(HttpDsl.http("Register")
                    .post("/api/v1/auth/register")
                    .header("Content-Type", "application/json")
                    .body(CoreDsl.StringBody(Templates.template)));

    {
        setUp(scenario.injectOpen(
                        OpenInjectionStep.atOnceUsers(50),
                        CoreDsl.rampUsers(50).during(10),
                        CoreDsl.constantUsersPerSec(40).during(10).randomized()
                ).protocols(httpProtocol::protocol)
        );
    }
}