package ro.unibuc.hello.exception;

public class ProjectNotFoundException extends RuntimeException {

    private static final String projectNotFoundTamplate = "Project with id %s was not found";
    public ProjectNotFoundException(String projectId) {
        super(String.format(projectNotFoundTamplate, projectId));
    }

}
