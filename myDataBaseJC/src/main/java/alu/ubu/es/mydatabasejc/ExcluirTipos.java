/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alu.ubu.es.mydatabasejc;

/**
 *
 * @author jhuidobro
 */
public class ExcluirTipos {
    public static boolean isExcluido(String tipo){
        boolean retorno = false;
        switch (tipo) {
            case "interface java.sql.Blob": ;
            case "interface java.sql.Clob": ;
            case "interface java.sql.NClob": ;
            case "interface java.sql.Statement": retorno = true;
        }
        return retorno;
    }
}
