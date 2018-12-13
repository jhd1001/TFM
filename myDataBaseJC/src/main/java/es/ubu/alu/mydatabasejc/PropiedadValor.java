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
public class PropiedadValor {

    private String propiedad;
    private Object valor;

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof PropiedadValor)) return false;
        if (obj==null) return false;
        if (this.propiedad==null && ((PropiedadValor)obj).propiedad==null) return true;
        return this.propiedad.equalsIgnoreCase(((PropiedadValor)obj).propiedad);
    }

    public PropiedadValor(String propiedad, Object valor) {
        this.propiedad = propiedad;
        this.valor = valor;
    }

    public String getPropiedad() {
        return propiedad;
    }

    public void setPropiedad(String propiedad) {
        this.propiedad = propiedad;
    }

    public Object getValor() {
        return valor;
    }

    public void setValor(Object valor) {
        this.valor = valor;
    }

}
