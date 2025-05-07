package ro.unibuc.hello.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.unibuc.hello.service.MetricsService;

@RestController
public class MetricsDemoController {

    private final MetricsService metricsService;

    public MetricsDemoController(MetricsService metricsService) {
        this.metricsService = metricsService;
    }

    @GetMapping("/metrics-demo")
    public String simulate() {
        metricsService.simulateMetrics();
        return "Metrics recorded!";
    }

    @GetMapping("/fail")
    public ResponseEntity<String> fail() {
        metricsService.simulateError();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Simulated error");
    }

    @GetMapping("/down")
    public String down() {
        metricsService.setAppIsDown();
        return "App marked as DOWN";
    }

    @GetMapping("/up")
    public String up() {
        metricsService.setAppIsUp();
        return "App marked as UP";
    }

    @GetMapping("/simulate-error")
    public String simulateError() {
        metricsService.simulateError();
        throw new RuntimeException("Simulated failure");
    }

    @GetMapping("/simulate-down")
    public String simulateDown() {
        metricsService.simulateDown();
        return "App reported as DOWN";
    }

    @GetMapping("/simulate-slow")
    public String simulateSlow() {
        metricsService.simulateSlow();
        return "Simulated slow response";
    }

}
