/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cofar.bean;

/**
 *
 * @author hvaldivia
 */
public class EmpaqueSecundarioExterno  implements Cloneable{
    private int codEmpaqueSecundarioExterno = 0;
    private String nombreEmpaqueSecundarioExterno = "";
    private String obsEmpaqueSecuntarioExterno = "";
    private EstadoReferencial estadoReferencial = new EstadoReferencial();

    public int getCodEmpaqueSecundarioExterno() {
        return codEmpaqueSecundarioExterno;
    }

    public void setCodEmpaqueSecundarioExterno(int codEmpaqueSecundarioExterno) {
        this.codEmpaqueSecundarioExterno = codEmpaqueSecundarioExterno;
    }

    public EstadoReferencial getEstadoReferencial() {
        return estadoReferencial;
    }

    public void setEstadoReferencial(EstadoReferencial estadoReferencial) {
        this.estadoReferencial = estadoReferencial;
    }

    public String getNombreEmpaqueSecundarioExterno() {
        return nombreEmpaqueSecundarioExterno;
    }

    public void setNombreEmpaqueSecundarioExterno(String nombreEmpaqueSecundarioExterno) {
        this.nombreEmpaqueSecundarioExterno = nombreEmpaqueSecundarioExterno;
    }

    public String getObsEmpaqueSecuntarioExterno() {
        return obsEmpaqueSecuntarioExterno;
    }

    public void setObsEmpaqueSecuntarioExterno(String obsEmpaqueSecuntarioExterno) {
        this.obsEmpaqueSecuntarioExterno = obsEmpaqueSecuntarioExterno;
    }
    @Override
    public EmpaqueSecundarioExterno clone()
    {
        EmpaqueSecundarioExterno clone = null;
        try
        {
            clone = (EmpaqueSecundarioExterno)super.clone();
        }
        catch(CloneNotSupportedException ex)
        {
            ex.printStackTrace();
            System.out.println("no se puede clonar");
        }
        return clone;
    }

}
