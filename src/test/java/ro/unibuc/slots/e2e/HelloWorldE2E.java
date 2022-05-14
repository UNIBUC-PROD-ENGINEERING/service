package ro.unibuc.slots.e2e;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(value = Cucumber.class)
@CucumberOptions(features = "src/test/resources", tags = "E2E")
public class HelloWorldE2E {
}
