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
public class Menu {
    private String action;
    private String metodo;
    private String parametros;

    public String getAction() {return action;}

    public void setAction(String action) {this.action = action;}

    public String getMetodo() {return metodo;}

    public void setMetodo(String metodo) {this.metodo = metodo;}

    public String getParametros() {return parametros;}

    public void setParametros(String parametros) {this.parametros = parametros;}

    public Menu(String action, String metodo, String parametros) {
        this.action = action;
        this.metodo = metodo;
        this.parametros = parametros;
    }
    
    
}
