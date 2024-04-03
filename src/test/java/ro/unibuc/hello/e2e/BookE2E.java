package ro.unibuc.hello.e2e;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resources",
    glue = "ro.unibuc.hello.e2e.steps",
    tags = "@BookE2E"
)

public class BookE2E {
}
