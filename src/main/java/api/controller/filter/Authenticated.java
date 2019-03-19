package api.controller.filter;

import javax.ws.rs.NameBinding;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Authentication annotation for Authenticate middleware.
 */
@Inherited
@NameBinding
@Retention(RUNTIME)
@Target({TYPE, METHOD})
public @interface Authenticated {
}
