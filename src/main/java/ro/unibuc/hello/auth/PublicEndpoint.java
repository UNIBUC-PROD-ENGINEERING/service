package ro.unibuc.hello.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE}) // Can be used on methods or entire controllers
@Retention(RetentionPolicy.RUNTIME) // Available at runtime for reflection
public @interface PublicEndpoint {

}
