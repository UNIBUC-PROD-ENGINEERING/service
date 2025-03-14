package ro.unibuc.hello.exception;

public class RobotBusyException extends RuntimeException {
    public RobotBusyException(String robotId) {
        super("Robot with ID " + robotId + " already has an active order.");
    }
}
