package es.ubu.alu.mydatabasejc;

/**
 * Se utiliza para presentar al usuario información en forma propiedad/valor
 * @author <A HREF="mailto:jhd1001@alu.ubu.es">José Ignacio Huidobro</A>
 * @version 1.0
 */
public class PropiedadValor {

    private String propiedad;
    private Object valor;

    /**
     * Dos objetos PropiedadValor son iguales si son iguales sus
     * atributos propiedad
     * @param obj Objeto a comparar
     * @return true si las propiedades de ambos objetos son iguales ignorando
     * mayúsculas. false en caso contrario
     */
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
