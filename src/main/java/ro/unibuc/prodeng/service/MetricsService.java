package ro.unibuc.prodeng.service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
public class MetricsService {

    private final Counter usersCreatedCounter;
    private final Counter todosCreatedCounter;
    private final Counter applicationErrorsCounter;
    private final Counter todosCompletedCounter;
    private final Timer todoLookupTimer;

    private final AtomicInteger activeUsersGauge = new AtomicInteger(0);

    public MetricsService(MeterRegistry registry) {
        this.usersCreatedCounter = Counter.builder("app_users_created_total")
                .description("Total number of users created in the student planner")
                .tag("category", "business")
                .register(registry);

        this.todosCreatedCounter = Counter.builder("app_todos_created_total")
                .description("Total number of planner activities created")
                .tag("category", "domain")
                .register(registry);

        this.applicationErrorsCounter = Counter.builder("app_errors_total")
                .description("Total number of application errors")
                .tag("category", "error")
                .register(registry);

        this.todosCompletedCounter = Counter.builder("app_todos_completed_total")
                .description("Total number of planner activities marked as done")
                .tag("category", "business")
                .register(registry);

        this.todoLookupTimer = Timer.builder("app_todo_lookup_duration_seconds")
                .description("Time spent looking up planner activities")
                .tag("category", "performance")
                .register(registry);

        Gauge.builder("app_active_users", activeUsersGauge, AtomicInteger::get)
                .description("Current number of active users in the student planner")
                .tag("category", "resource")
                .register(registry);
    }

    public void recordUserCreated() {
        usersCreatedCounter.increment();
        activeUsersGauge.incrementAndGet();
    }

    public void recordTodoCreated() {
        todosCreatedCounter.increment();
    }

    public void recordApplicationError() {
        applicationErrorsCounter.increment();
    }

    public void recordTodoCompleted() {
        todosCompletedCounter.increment();
    }

    public Timer getTodoLookupTimer() {
        return todoLookupTimer;
    }
}