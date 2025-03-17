package ro.unibuc.hello.exception;

public class RobotNotFoundException extends RuntimeException {
    public RobotNotFoundException(String robotId) {
        super("Robot with ID " + robotId + " was not found.");
    }
}
