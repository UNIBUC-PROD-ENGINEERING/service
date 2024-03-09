package ro.tripleaunibuc.domain.auth.exception;

public class AuthenticationException extends RuntimeException {

	public AuthenticationException(String message, Throwable cause) {
		super(message, cause);
	}

}