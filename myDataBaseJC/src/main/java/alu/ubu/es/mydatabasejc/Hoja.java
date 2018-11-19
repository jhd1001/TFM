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
public class Hoja {
    private int id;
    private int padre;
    private String texto;

    public Hoja(int id, int padre, String texto) {
        this.id = id;
        this.padre = padre;
        this.texto = texto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPadre() {
        return padre;
    }

    public void setPadre(int padre) {
        this.padre = padre;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
    
    
}
