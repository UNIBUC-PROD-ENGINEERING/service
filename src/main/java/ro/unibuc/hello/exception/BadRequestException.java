package ro.unibuc.hello.exception;

import java.util.HashMap;

public class BadRequestException extends RuntimeException{
    private HashMap<String, String> problems;
    public HashMap<String, String> getProblems() { return this.problems; }

    public BadRequestException(HashMap<String, String> problems) {
        super("Bad Request");

        this.problems = problems;
    }
}
