package ro.unibuc.hello.service;

import io.micrometer.core.instrument.*;
import org.springframework.stereotype.Service;

@Service
public class MetricsService {

    private final Counter helloCounter;
    private final Timer helloTimer;
    private final DistributionSummary payloadSummary;
    private final LongTaskTimer longTaskTimer;
    private final Counter errorCounter;
    private final Gauge availabilityGauge;
    private final Gauge randomGauge;

    private double randomValue;
    private volatile int appIsUp = 1; // 1 = up, 0 = down

    private double availabilityStatus = 1;

    public MetricsService(MeterRegistry registry) {

        this.helloCounter = registry.counter("custom_hello_requests_total");

        this.helloTimer = registry.timer("custom_hello_timer_seconds");

        this.payloadSummary = DistributionSummary.builder("custom_payload_size_bytes")
                .baseUnit("bytes")
                .description("Payload size in bytes")
                .register(registry);

        this.longTaskTimer = LongTaskTimer.builder("custom_long_task_timer_seconds")
                .register(registry);

        this.errorCounter = Counter.builder("app_error_count_total")
                .description("Total number of app errors")
                .register(registry);

        this.availabilityGauge = Gauge.builder("app_availability_status", this, MetricsService::getAppIsUp)
                .description("1 = UP, 0 = DOWN")
                .register(registry);

        this.randomGauge = Gauge.builder("custom_random_value", this, MetricsService::getRandomValue)
                .description("A random double value that changes")
                .register(registry);
    }

    public void simulateMetrics() {
        helloCounter.increment();

        helloTimer.record(() -> {
            try {
                Thread.sleep((long) (Math.random() * 100));
            } catch (InterruptedException ignored) {
            }
        });

        payloadSummary.record((Math.random() * 1000) + 100);

        longTaskTimer.record(() -> {
            try {
                Thread.sleep((long) (Math.random() * 150));
            } catch (InterruptedException ignored) {
            }
        });

        randomValue = Math.random() * 100;
    }

    public void simulateError() {
        errorCounter.increment();
    }

    public void setAppIsDown() {
        this.appIsUp = 0;
    }

    public void setAppIsUp() {
        this.appIsUp = 1;
    }

    public double getRandomValue() {
        return randomValue;
    }

    public int getAppIsUp() {
        return appIsUp;
    }

    public void simulateSlow() {
        helloTimer.record(() -> {
            try {
                Thread.sleep(1000); // >= 1 sec
            } catch (InterruptedException ignored) {
            }
        });
    }

    public void simulateDown() {
        this.availabilityStatus = 0;
    }
}
