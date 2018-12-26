/*
 * Define si los métodos a los que acompaña deben ser presentados con 
 * un link para su continuación en el seguimiento de la información
 * que pueden presentar en pantalla para cada registro devuelto
 */
package es.ubu.alu.mydatabasejc.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author jhuidobro
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MetaDataLink {
    // devuelve la acción struts que debe ejecutarse
    String action();
    // devuelve el namespace en el que debe ejecutarse la acción
    String namespace();
    // devuelve los parametros que deben incluirse en el link,
    // en formato array String
    String[] parametros();
    int columnNumber();
}
