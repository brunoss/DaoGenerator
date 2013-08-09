package g14.daoGenerator.strucutre;

import g14.daoGenerator.strucutre.cmdTypes.Read;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SqlCmd {
    String cmd();

    Class<?> type() default Read.class;
}
