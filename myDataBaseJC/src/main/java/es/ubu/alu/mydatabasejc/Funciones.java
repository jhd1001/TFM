/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ubu.alu.mydatabasejc;

/**
 *
 * @author jhuidobro
 */
public abstract class Funciones {
    public static String getCatalog(String catalogo, String esquema) {
        String separador = "";
        if (nz(esquema).length()!=0 && nz(catalogo).length()!=0)
            separador = ".";
        return nz(catalogo) + separador + nz(esquema);
    }
    
    public static String nz(String cadena) {
        return cadena==null ? "" : cadena;
    }
}
