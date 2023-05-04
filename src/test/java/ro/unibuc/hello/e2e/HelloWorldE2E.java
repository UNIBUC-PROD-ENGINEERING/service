package ro.unibuc.hello.e2e;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/java/ro/unibuc/hello/e2e")
public class HelloWorldE2E {
}
