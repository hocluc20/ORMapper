package at.kaindorf.ormapper.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE) // define annotation for class-level
@Retention(RetentionPolicy.RUNTIME) //sie ist zu laufzeit verfügbar und wir können über die
// reflections schauen, ob sie vorhanden bzw aktiv ist.
public @interface Entity {
    String name () default "";
}
