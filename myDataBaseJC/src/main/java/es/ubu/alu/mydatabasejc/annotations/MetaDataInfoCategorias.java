package es.ubu.alu.mydatabasejc.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Define la anotación para categorizar los métodos de DatabaseMetaDataImpl
 * @author jhuidobro
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MetaDataInfoCategorias {
    String categoria() default "Básica";
}
