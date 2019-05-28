package es.ubu.alu.mydatabasejc.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Define la anotación para marcar los métodos de DatabaseMetaDataImpl 
 * que deben proporcionar la información para establecer un link hacia
 * otra pantalla y cuales han de ser los parámetros a enviar en la
 * request de ese link
 * 
 * @author <A HREF="mailto:jhd1001@alu.ubu.es">José Ignacio Huidobro</A>
 * @version 1.0
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
    // marca el número de la columna en la que debe situarse el link
    int columnNumber();
}
